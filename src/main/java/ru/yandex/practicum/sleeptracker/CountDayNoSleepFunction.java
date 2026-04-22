package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;
import java.time.Duration;


public class CountDayNoSleepFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

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

        long countNightSessions = sessions.stream()
                .filter(session -> (
                                (!session.getStartSession().toLocalDate().equals(session.getEndSession().toLocalDate())) ||
                                        (session.getStartSession().toLocalDate().equals(session.getEndSession().toLocalDate()) &&
                                                (session.getStartSession().toLocalTime().isBefore(LocalTime.MIDNIGHT.plusHours(6))))

                        )
                )
                .map(session -> session.getStartSession().toLocalDate())
                .distinct()
                .count();

        return new SleepAnalysisResult(
                "Количество ночей без сна",
                String.valueOf(allDays - countNightSessions)
        );
    }
}