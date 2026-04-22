package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountBadQualitySessionFunctionTest {

    private final CountBadQualitySessionFunction function = new CountBadQualitySessionFunction();

    @Test
    void countBadQualitySessionFunctionTest() {
        try {
            List<SleepingSession> sleepingSessions = new ArrayList<>();
            sleepingSessions.add(new SleepingSession("01.10.25 23:00;02.10.25 00:00;GOOD"));
            sleepingSessions.add(new SleepingSession("02.10.25 23:00;03.10.25 00:00;GOOD"));
            sleepingSessions.add(new SleepingSession("03.10.25 23:00;04.10.25 00:00;BAD"));

            SleepAnalysisResult result = function.apply(sleepingSessions);

            assertNotNull(result, "Результат не должен быть null");
            assertEquals("1", result.getAnalyseResult(), "Количество сессий с плохим качеством сна 1");
            assertEquals("Количество сессий с плохим качеством сна: 1", result.toString(), "Некорректное описание");

        } catch (SleepSessionErrors exception) {
            fail("Не должно возникать исключений");
        }
    }

    @Test
    void countBadQualitySessionFunctionTestIsZero() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult result = function.apply(sleepingSessions);
        assertNotNull(result, "Результат не должен быть null");
        assertEquals("0", result.getAnalyseResult(), "Количество сессий с плохим качеством сна: 0");
        assertEquals("Количество сессий с плохим качеством сна: 0", result.toString(), "Некорректное описание");
    }
}