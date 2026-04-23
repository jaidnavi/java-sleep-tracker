package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class CalculateChronotypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final int OWL_SLEEP_TIME = 23;     // время засыпания для Сов
    private static final int OWL_AWAKENING_TIME = 9;  // время пробуждения для Сов
    private static final int LARK_SLEEP_TIME = 22;    // время засыпания для Жаворонков
    private static final int LARK_AWAKENING_TIME = 7; // время пробуждения для Жаворонков
    private static final int FINAL_NIGHT_TIME = 6;    // время окончания ночи

    private List<SleepingSession> getSessionsOwl(List<SleepingSession> sessions) {
        // «Сова» — если время засыпания было после 23:00 (либо ночью, до 6 утра - в ночное время), а время пробуждения — после 9:00
        return sessions.stream()
                .filter(session -> (session.getStartSession().toLocalTime().isAfter(LocalTime.MIDNIGHT.plusHours(OWL_SLEEP_TIME)) ||
                        session.getStartSession().toLocalTime().isBefore(LocalTime.MIDNIGHT.plusHours(FINAL_NIGHT_TIME)))
                )
                .filter(session -> (session.getEndSession().toLocalTime().isAfter(LocalTime.MIDNIGHT.plusHours(OWL_AWAKENING_TIME)))
                )
                .toList();
    }

    private List<SleepingSession> getSessionsLark(List<SleepingSession> sessions) {
        // «Жаворонок» — если время засыпания было до 22:00 (но не в прошлую ночь, до 6 утра), а время пробуждения до — 7:00.
        return sessions.stream()
                .filter(session -> (session.getStartSession().toLocalTime().isBefore(LocalTime.MIDNIGHT.plusHours(LARK_SLEEP_TIME)) &&
                                session.getStartSession().toLocalTime().isAfter(LocalTime.MIDNIGHT.plusHours(FINAL_NIGHT_TIME))
                        )
                )
                .filter(session -> (session.getEndSession().toLocalTime().isBefore(LocalTime.MIDNIGHT.plusHours(LARK_AWAKENING_TIME)))
                )
                .toList();
    }

    private List<SleepingSession> getSessionsDove(List<SleepingSession> nightSessions, List<SleepingSession> nightSessionsOwl, List<SleepingSession> nightSessionsLark) {
        return nightSessions.stream()
                .filter(session -> !nightSessionsLark.contains(session))
                .filter(session -> !nightSessionsOwl.contains(session))
                .toList();
    }

    private Chronotype getChronotype(List<SleepingSession> nightSessionsOwl,
                                     List<SleepingSession> nightSessionsLark,
                                     List<SleepingSession> nightSessionsDove) {
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

        if (maxCount == countOwlSession && countOwlSession != countLarkSession) {
            return Chronotype.OWL;
        } else if (maxCount == countLarkSession && countOwlSession != countLarkSession) {
            return Chronotype.LARK;
        } else {
            return Chronotype.DOVE;
        }
    }

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        List<SleepingSession> nightSessions = SleepingSession.getNightSessions(sessions);
        List<SleepingSession> nightSessionsOwl = getSessionsOwl(nightSessions);
        List<SleepingSession> nightSessionsLark = getSessionsLark(nightSessions);
        List<SleepingSession> nightSessionsDove = getSessionsDove(nightSessions, nightSessionsOwl, nightSessionsLark);
        Chronotype chronotype = getChronotype(nightSessionsOwl, nightSessionsLark, nightSessionsDove);
        return new SleepAnalysisResult("Хронотип пользователя", chronotype.getDescription());
    }
}
