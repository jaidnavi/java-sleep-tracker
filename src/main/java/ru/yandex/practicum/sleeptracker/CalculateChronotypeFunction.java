package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class CalculateChronotypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        List<SleepingSession> nightSessions = SleepingSession.getNightSessions(sessions);

        List<SleepingSession> nightSessionsOwl = nightSessions.stream()
                .filter(session -> (session.getStartSession().toLocalTime().isAfter(LocalTime.MIDNIGHT.plusHours(23)) ||
                        session.getStartSession().toLocalTime().isBefore(LocalTime.MIDNIGHT.plusHours(6)))
                )
                .filter(session -> (session.getEndSession().toLocalTime().isAfter(LocalTime.MIDNIGHT.plusHours(9)))
                )
                .toList();

        List<SleepingSession> nightSessionsLark = nightSessions.stream()
                .filter(session -> (session.getStartSession().toLocalTime().isBefore(LocalTime.MIDNIGHT.plusHours(22)) &&
                                session.getStartSession().toLocalTime().isAfter(LocalTime.MIDNIGHT.plusHours(6))
                        )
                )
                .filter(session -> (session.getEndSession().toLocalTime().isBefore(LocalTime.MIDNIGHT.plusHours(7)))
                )
                .toList();

        List<SleepingSession> nightSessionsDove = nightSessions.stream()
                .filter(session -> !nightSessionsLark.contains(session))
                .filter(session -> !nightSessionsOwl.contains(session))
                .toList();

        long countOwlSession = nightSessionsOwl.stream()
                .map(session -> session.getStartSession().toLocalDate())
                .distinct()
                .count();

        long countLarkSession = nightSessionsLark.stream()
                .map(session -> session.getStartSession().toLocalDate())
                .distinct()
                .count();

        long countDoveSession = nightSessionsDove.stream()
                .map(session -> session.getStartSession().toLocalDate())
                .distinct()
                .count();

        long maxCount = Math.max(Math.max(countOwlSession, countLarkSession), countDoveSession);

        String chronotype;
        if (maxCount == countOwlSession && countOwlSession != countLarkSession) {
            chronotype = "Сова";
        } else if (maxCount == countLarkSession && countOwlSession != countLarkSession) {
            chronotype = "Жаворонок";
        } else {
            chronotype = "Голубь";
        }

        return new SleepAnalysisResult("Хронотип пользователя", chronotype);
    }
}
