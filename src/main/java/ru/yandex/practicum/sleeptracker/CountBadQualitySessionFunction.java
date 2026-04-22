package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class CountBadQualitySessionFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long badSessionsCount = sessions.stream()
                .filter(session -> session.getSleepQuality() == SleepQuality.BAD)
                .count();

        return new SleepAnalysisResult("Количество сессий с плохим качеством сна",
                String.valueOf(badSessionsCount));
    }
}