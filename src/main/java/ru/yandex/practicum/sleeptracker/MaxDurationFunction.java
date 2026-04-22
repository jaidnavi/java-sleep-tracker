package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;
import java.time.Duration;
import java.util.Comparator;

public class MaxDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Duration maxMinutes = sessions.stream()
                .map(session -> Duration.between(
                        session.getStartSession(),
                        session.getEndSession()
                ))
                .max(Comparator.comparing(Duration::toMinutes))
                .orElse(Duration.ofMinutes(0));

        return new SleepAnalysisResult("Максимальная продолжительность сессии (в минутах): ",
                String.valueOf(maxMinutes.toMinutes()));
    }
}