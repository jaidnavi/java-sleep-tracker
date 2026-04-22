package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountSessionsFunctionTest {

    private final CountSessionsFunction function = new CountSessionsFunction();

    @Test
    void countSessionsFunctionTest() {
        try {
            List<SleepingSession> sleepingSessions = new ArrayList<>();
            sleepingSessions.add(new SleepingSession("01.10.25 23:15;02.10.25 07:30;GOOD"));
            sleepingSessions.add(new SleepingSession("02.10.25 23:15;03.10.25 07:30;GOOD"));
            sleepingSessions.add(new SleepingSession("03.10.25 23:15;04.10.25 07:30;BAD"));

            SleepAnalysisResult result = function.apply(sleepingSessions);

            assertNotNull(result, "Результат не должен быть null");
            assertEquals("3", result.getAnalyseResult(), "Количество сессий должно быть 3");
            assertEquals("Общее количество сессий: 3", result.toString(), "Некорректное описание");

        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void countSessionsFunctionTestIsZero() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult result = function.apply(sleepingSessions);
        assertNotNull(result, "Результат не должен быть null");
        assertEquals("0", result.getAnalyseResult(), "Количество сессий должно быть 0");
        assertEquals("Общее количество сессий: 0", result.toString(), "Некорректное описание");
    }
}