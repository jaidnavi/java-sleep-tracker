package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountDayNoSleepFunctionTest {

    private final CountDayNoSleepFunction function = new CountDayNoSleepFunction();

    @Test
    void countDayNoSleepFunctionTestStartAfterNoon() {
        try {
            List<SleepingSession> sleepingSessions = new ArrayList<>();
            sleepingSessions.add(new SleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD"));
            sleepingSessions.add(new SleepingSession("02.10.25 23:15;03.10.25 07:30;GOOD"));
            sleepingSessions.add(new SleepingSession("03.10.25 23:15;04.10.25 07:30;BAD"));
            sleepingSessions.add(new SleepingSession("10.10.25 23:15;11.10.25 07:30;BAD"));

            SleepAnalysisResult result = function.apply(sleepingSessions);

            assertNotNull(result, "Результат не должен быть null");
            assertEquals("6", result.getAnalyseResult(), "Количество ночей без сна должно быть 6");
            assertEquals("Количество ночей без сна: 6", result.toString(), "Некорректное описание");

        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void countDayNoSleepFunctionTestStartBeforeNoon() {
        try {
            List<SleepingSession> sleepingSessions = new ArrayList<>();
            sleepingSessions.add(new SleepingSession("01.10.25 10:15;02.10.25 07:30;GOOD"));
            sleepingSessions.add(new SleepingSession("02.10.25 23:15;03.10.25 07:30;GOOD"));
            sleepingSessions.add(new SleepingSession("03.10.25 23:15;04.10.25 07:30;BAD"));
            sleepingSessions.add(new SleepingSession("10.10.25 23:15;11.10.25 07:30;BAD"));

            SleepAnalysisResult result = function.apply(sleepingSessions);

            assertNotNull(result, "Результат не должен быть null");
            assertEquals("7", result.getAnalyseResult(), "Количество ночей без сна должно быть 7");
            assertEquals("Количество ночей без сна: 7", result.toString(), "Некорректное описание");

        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void countDayNoSleepFunctionIsZero() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult result = function.apply(sleepingSessions);
        assertNotNull(result, "Результат не должен быть null");
        assertEquals("0", result.getAnalyseResult(), "Количество ночей без сна должно быть 0");
        assertEquals("Количество ночей без сна: 0", result.toString(), "Некорректное описание");
    }
}