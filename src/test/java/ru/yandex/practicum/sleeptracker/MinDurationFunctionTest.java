package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MinDurationFunctionTest {
    private final MinDurationFunction function = new MinDurationFunction();

    @Test
    void minDurationFunctionTestTest() {
        try {
            List<SleepingSession> sleepingSessions = new ArrayList<>();
            sleepingSessions.add(new SleepingSession("01.10.25 23:00;02.10.25 01:30;GOOD"));
            sleepingSessions.add(new SleepingSession("02.10.25 23:00;03.10.25 01:40;GOOD"));
            sleepingSessions.add(new SleepingSession("03.10.25 23:00;04.10.25 01:50;BAD"));

            SleepAnalysisResult result = function.apply(sleepingSessions);

            assertNotNull(result, "Результат не должен быть null");
            assertEquals("150", result.getAnalyseResult(), "Минимальная продолжительность сессии (в минутах): 150");
            assertEquals("Минимальная продолжительность сессии (в минутах): 150", result.toString(), "Некорректное описание");

        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void minDurationFunctionTestIsZero() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult result = function.apply(sleepingSessions);
        assertNotNull(result, "Результат не должен быть null");
        assertEquals("0", result.getAnalyseResult(), "Минимальная продолжительность сессии (в минутах): 0");
        assertEquals("Минимальная продолжительность сессии (в минутах): 0", result.toString(), "Некорректное описание");
    }
}