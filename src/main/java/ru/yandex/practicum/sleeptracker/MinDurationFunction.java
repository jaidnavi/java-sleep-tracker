package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;
import java.time.Duration;
import java.util.Comparator;

public class MinDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Duration minMinutes = sessions.stream()
                .map(session -> Duration.between(
                        session.getStartSession(),
                        session.getEndSession()
                ))
                .min(Comparator.comparing(Duration::toMinutes))
                .orElse(Duration.ofMinutes(0));

        return new SleepAnalysisResult("Минимальная продолжительность сессии (в минутах): ",
                String.valueOf(minMinutes.toMinutes()));
    }
}