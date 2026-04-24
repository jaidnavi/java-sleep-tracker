package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class SleepingSessionTest {

    @Test
    void createSleepingSession() {
        try {
            SleepingSession sessionOne = new SleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD");
        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений, строка корректна");
        }
    }

    @Test
    void tryCreateNullSleepingSession() {
        try {
            SleepingSession sessionOne = new SleepingSession("");
            fail("Должно возникнуть SleepSessionErrors исключение, строка пуста");
        } catch (SleepSessionErrors ignored) {
        }
    }

    @Test
    void tryCreateErrSleepingSessionString() {
        try {
            SleepingSession sessionOne = new SleepingSession("01.10.25 23:15;02.10.25 07:30;!!!!");
            fail("Должно возникнуть SleepSessionErrors исключение, строка недопустимого формата");
        } catch (SleepSessionErrors ignored) {
        }
    }

    @Test
    void tryCreateErrSleepingSessionOverflow24() {
        try {
            SleepingSession sessionOne = new SleepingSession("01.10.25 23:15;03.10.25 07:30;GOOD");
            fail("Должно возникнуть SleepSessionErrors исключение, сон более 24 часов");
        } catch (SleepSessionErrors ignored) {
        }
    }

    @Test
    void tryCreateErrSleepingSessionRevert() {
        try {
            SleepingSession sessionOne = new SleepingSession("01.10.25 00:01;01.10.25 00:00;GOOD");
            fail("Должно возникнуть SleepSessionErrors исключение, пробуждение раньше засыпания");
        } catch (SleepSessionErrors ignored) {
        }
    }

    @Test
    void testEquals() {

        try {
            SleepingSession sessionOne = new SleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD");
            SleepingSession sessionTwo = new SleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD");
            SleepingSession sessionThree = new SleepingSession("01.10.25 23:15;02.10.25 07:30;BAD");
            if (!sessionOne.equals(sessionTwo)) {
                fail("Сессии должны быть одинаковы");
            }
            if (sessionOne.equals(sessionThree)) {
                fail("Сессии должны быть разными");
            }
        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void getStartSessionCorrect() {
        try {
            SleepingSession sessionOne = new SleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD");
            if (!sessionOne.getStartSession().equals(LocalDateTime.of(2025, 10, 1, 23, 15))) {
                fail("Значение даты начала сессии определяется не корректно");
            }
        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void getEndSessionCorrect() {
        try {
            SleepingSession sessionOne = new SleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD");
            if (!sessionOne.getEndSession().equals(LocalDateTime.of(2025, 10, 2, 7, 30))) {
                fail("Значение даты окончания сессии определяется не корректно");
            }
        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }


    @Test
    void getSleepQualityCorrect() {
        try {
            SleepingSession sessionOne = new SleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD");
            if (!sessionOne.getSleepQuality().equals(SleepQuality.GOOD)) {
                fail("Значение качества сна сессии определяется не корректно для " + SleepQuality.GOOD);
            }
            SleepingSession sessionTwo = new SleepingSession("01.10.25 23:15;02.10.25 07:30;BAD");
            if (!sessionTwo.getSleepQuality().equals(SleepQuality.BAD)) {
                fail("Значение качества сна сессии определяется не корректно для " + SleepQuality.BAD);
            }
            SleepingSession sessionThree = new SleepingSession("01.10.25 23:15;02.10.25 07:30;NORMAL");
            if (!sessionThree.getSleepQuality().equals(SleepQuality.NORMAL)) {
                fail("Значение качества сна сессии определяется не корректно для " + SleepQuality.NORMAL);
            }
        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void getNightSessionsTest() {
        try {
            List<SleepingSession> sleepingSessions = new ArrayList<>();
            sleepingSessions.add(new SleepingSession("30.09.25 20:00;30.09.25 22:00;BAD"));
            sleepingSessions.add(new SleepingSession("01.10.25 23:00;02.10.25 00:00;GOOD"));
            sleepingSessions.add(new SleepingSession("02.10.25 23:00;03.10.25 00:01;GOOD"));
            sleepingSessions.add(new SleepingSession("04.10.25 00:00;04.10.25 00:01;GOOD"));
            sleepingSessions.add(new SleepingSession("05.10.25 00:01;05.10.25 00:02;GOOD"));
            sleepingSessions.add(new SleepingSession("05.10.25 05:59;05.10.25 06:00;GOOD"));
            sleepingSessions.add(new SleepingSession("06.10.25 05:59;06.10.25 06:01;GOOD"));
            sleepingSessions.add(new SleepingSession("07.10.25 06:01;07.10.25 06:02;BAD"));
            sleepingSessions.add(new SleepingSession("08.10.25 06:01;08.10.25 23:59;BAD"));

            List<SleepingSession> nightSessions = SleepingSession.getNightSessions(sleepingSessions);

            for (SleepingSession session : nightSessions) {
                assertEquals(SleepQuality.GOOD, session.getSleepQuality(), "Среди отобранных есть не ночные сессии:" + session.getStartSession() + " - " + session.getEndSession());
            }

            assertEquals(6, nightSessions.size(), "Количество ночных сессий должно быть 6");

        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

}