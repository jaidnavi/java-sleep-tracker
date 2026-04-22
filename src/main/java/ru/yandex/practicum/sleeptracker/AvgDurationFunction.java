package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;
import java.time.Duration;
import java.lang.Long;
import java.util.stream.Collectors;

public class AvgDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long avgMinutes = 1;

        return new SleepAnalysisResult("Средняя продолжительность сессии (в минутах): ",
                String.valueOf(avgMinutes));
    }
}