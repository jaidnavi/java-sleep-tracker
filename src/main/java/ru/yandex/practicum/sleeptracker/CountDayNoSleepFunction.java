package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;
import java.time.Duration;


public class CountDayNoSleepFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(
                    "Количество ночей без сна",
                    String.valueOf(0)
            );
        }

        LocalDateTime firstStart = sessions.get(0).getStartSession();
        LocalDateTime lastEnd = sessions.get(sessions.size() - 1).getEndSession().toLocalDate().atStartOfDay();

        LocalDateTime firstStartDate;
        if (firstStart.toLocalTime().isBefore(LocalTime.NOON)) {
            firstStartDate = firstStart.toLocalDate().minusDays(1).atStartOfDay();
        } else {
            firstStartDate = firstStart.toLocalDate().atStartOfDay();
        }

        Duration period = Duration.between(firstStartDate, lastEnd);
        long allDays = period.toDays();

        List<SleepingSession> nightSessions = SleepingSession.getNightSessions(sessions);

        long countNightSessions = nightSessions.stream()
                .map(session -> session.getStartSession().toLocalDate())
                .distinct()
                .count();

        return new SleepAnalysisResult(
                "Количество ночей без сна",
                String.valueOf(allDays - countNightSessions)
        );
    }
}