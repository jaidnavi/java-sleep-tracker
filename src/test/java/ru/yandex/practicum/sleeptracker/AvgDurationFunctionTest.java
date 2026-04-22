package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AvgDurationFunctionTest {


    private final AvgDurationFunction function = new AvgDurationFunction();

    @Test
    void AvgDurationFunctionTest() {
        try {
            List<SleepingSession> sleepingSessions = new ArrayList<>();
            sleepingSessions.add(new SleepingSession("01.10.25 23:00;02.10.25 00:00;GOOD"));
            sleepingSessions.add(new SleepingSession("02.10.25 23:00;03.10.25 00:00;GOOD"));
            sleepingSessions.add(new SleepingSession("03.10.25 23:00;04.10.25 00:00;BAD"));

            SleepAnalysisResult result = function.apply(sleepingSessions);

            assertNotNull(result, "Результат не должен быть null");
            assertEquals("60.0", result.getAnalyseResult(), "Средняя продолжительность должна быть 60");
            assertEquals("Средняя продолжительность сессии (в минутах): 60.0", result.toString(), "Некорректное описание");

        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void AvgDurationFunctionTestIsZero() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult result = function.apply(sleepingSessions);
        assertNotNull(result, "Результат не должен быть null");
        assertEquals("0.0", result.getAnalyseResult(), "Средняя продолжительность сессии (в минутах) должно быть 0");
        assertEquals("Средняя продолжительность сессии (в минутах): 0.0", result.toString(), "Некорректное описание");
    }
}