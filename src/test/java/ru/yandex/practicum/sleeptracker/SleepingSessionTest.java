package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class SleepingSessionTest {

    @Test
    void createSleepingSession() {
        try {
            SleepingSession sessionOne = new SleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD");
        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void tryCreateNullSleepingSession() {
        try {
            SleepingSession sessionOne = new SleepingSession("");
            fail("Должно возникнуть SleepSessionErrors исключение");
        } catch (SleepSessionErrors ignored) {
        }
    }

    @Test
    void tryCreateErrSleepingSession() {
        try {
            SleepingSession sessionOne = new SleepingSession("01.10.25 23:15;02.10.25 07:30;!!!!");
            fail("Должно возникнуть SleepSessionErrors исключение");
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
}