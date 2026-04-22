package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculateChronotypeFunctionTest {

    private final CalculateChronotypeFunction function = new CalculateChronotypeFunction();

    @Test
    void calculateChronotypeFunctionDoveIsDove() {
        try {
            List<SleepingSession> sleepingSessions = new ArrayList<>();
            sleepingSessions.add(new SleepingSession("01.10.25 23:00;02.10.25 00:00;GOOD")); //Голубь
            sleepingSessions.add(new SleepingSession("02.10.25 23:00;03.10.25 00:00;GOOD")); //Голубь
            sleepingSessions.add(new SleepingSession("03.10.25 23:00;04.10.25 00:00;BAD"));  //Голубь
            sleepingSessions.add(new SleepingSession("04.10.25 09:00;04.10.25 10:00;BAD"));  //Не ночь
            sleepingSessions.add(new SleepingSession("05.10.25 09:00;05.10.25 10:00;BAD"));  //Не ночь
            sleepingSessions.add(new SleepingSession("06.10.25 09:00;06.10.25 10:00;BAD"));  //Не ночь
            sleepingSessions.add(new SleepingSession("07.10.25 09:00;07.10.25 10:00;BAD"));  //Не ночь

            SleepAnalysisResult result = function.apply(sleepingSessions);

            assertNotNull(result, "Результат не должен быть null");
            assertEquals("Голубь", result.getAnalyseResult(), "Хронотип пользователя должен быть Голубь");
            assertEquals("Хронотип пользователя: Голубь", result.toString(), "Некорректное описание");

        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void calculateChronotypeFunctionOwlIsOwl() {
        try {
            List<SleepingSession> sleepingSessions = new ArrayList<>();
            sleepingSessions.add(new SleepingSession("01.10.25 23:01;02.10.25 09:01;GOOD"));  //Сова
            sleepingSessions.add(new SleepingSession("02.10.25 23:01;03.10.25 09:01;GOOD"));  //Сова
            sleepingSessions.add(new SleepingSession("03.10.25 23:00;04.10.25 09:00;BAD"));   //Голубь
            sleepingSessions.add(new SleepingSession("04.10.25 09:00;04.10.25 10:00;BAD"));  //Не ночь
            sleepingSessions.add(new SleepingSession("05.10.25 09:00;05.10.25 10:00;BAD"));  //Не ночь
            sleepingSessions.add(new SleepingSession("06.10.25 09:00;06.10.25 10:00;BAD"));  //Не ночь
            sleepingSessions.add(new SleepingSession("07.10.25 09:00;07.10.25 10:00;BAD"));  //Не ночь

            SleepAnalysisResult result = function.apply(sleepingSessions);

            assertNotNull(result, "Результат не должен быть null");
            assertEquals("Сова", result.getAnalyseResult(), "Хронотип пользователя должен быть Сова");
            assertEquals("Хронотип пользователя: Сова", result.toString(), "Некорректное описание");

        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void calculateChronotypeFunctionLarkIsLark() {
        try {
            List<SleepingSession> sleepingSessions = new ArrayList<>();
            sleepingSessions.add(new SleepingSession("01.10.25 21:59;02.10.25 06:59;GOOD")); //Жаворонок
            sleepingSessions.add(new SleepingSession("02.10.25 21:59;03.10.25 06:59;GOOD")); //Жаворонок
            sleepingSessions.add(new SleepingSession("03.10.25 22:00;04.10.25 07:00;BAD"));  //Голубь
            sleepingSessions.add(new SleepingSession("04.10.25 09:00;04.10.25 10:00;BAD"));  //Не ночь
            sleepingSessions.add(new SleepingSession("05.10.25 09:00;05.10.25 10:00;BAD"));  //Не ночь
            sleepingSessions.add(new SleepingSession("06.10.25 09:00;06.10.25 10:00;BAD"));  //Не ночь
            sleepingSessions.add(new SleepingSession("07.10.25 09:00;07.10.25 10:00;BAD"));  //Не ночь

            SleepAnalysisResult result = function.apply(sleepingSessions);

            assertNotNull(result, "Результат не должен быть null");
            assertEquals("Жаворонок", result.getAnalyseResult(), "Хронотип пользователя должен быть Жаворонок");
            assertEquals("Хронотип пользователя: Жаворонок", result.toString(), "Некорректное описание");

        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void calculateChronotypeFunctionOwlLarkIsDove() {
        try {
            List<SleepingSession> sleepingSessions = new ArrayList<>();
            sleepingSessions.add(new SleepingSession("01.10.25 21:59;02.10.25 06:59;GOOD")); //Жаворонок
            sleepingSessions.add(new SleepingSession("02.10.25 21:59;03.10.25 06:59;GOOD")); //Жаворонок
            sleepingSessions.add(new SleepingSession("03.10.25 23:01;04.10.25 09:01;GOOD")); //Сова
            sleepingSessions.add(new SleepingSession("04.10.25 23:01;05.10.25 09:01;GOOD")); //Сова
            sleepingSessions.add(new SleepingSession("05.10.25 22:00;06.10.25 07:00;BAD"));  //Голубь
            sleepingSessions.add(new SleepingSession("06.10.25 09:00;06.10.25 10:00;BAD"));  //Не ночь
            sleepingSessions.add(new SleepingSession("07.10.25 09:00;07.10.25 10:00;BAD"));  //Не ночь
            sleepingSessions.add(new SleepingSession("08.10.25 09:00;08.10.25 10:00;BAD"));  //Не ночь
            sleepingSessions.add(new SleepingSession("09.10.25 09:00;09.10.25 10:00;BAD"));  //Не ночь

            SleepAnalysisResult result = function.apply(sleepingSessions);

            assertNotNull(result, "Результат не должен быть null");
            assertEquals("Голубь", result.getAnalyseResult(), "Хронотип пользователя должен быть Голубь");
            assertEquals("Хронотип пользователя: Голубь", result.toString(), "Некорректное описание");

        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void calculateChronotypeFunctionNullIsDove() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult result = function.apply(sleepingSessions);
        assertNotNull(result, "Результат не должен быть null");
        assertEquals("Голубь", result.getAnalyseResult(), "Хронотип пользователя должен быть Голубь");
        assertEquals("Хронотип пользователя: Голубь", result.toString(), "Некорректное описание");
    }
}