package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;
import java.time.Duration;

public class AvgDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        double avgMinutes = sessions.stream()
                .mapToLong(session -> Duration.between(
                        session.getStartSession(),
                        session.getEndSession()).toMinutes())
                .average()
                .orElse(0.0);

        return new SleepAnalysisResult("Средняя продолжительность сессии (в минутах)",
                String.valueOf(avgMinutes));
    }
}