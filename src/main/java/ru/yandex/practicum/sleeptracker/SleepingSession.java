package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SleepingSession {
    private final LocalDateTime startSession;
    private final LocalDateTime endSession;
    private final SleepQuality sleepQuality;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public SleepingSession(String sleepingSession) throws SleepSessionErrors {
        String[] parts = sleepingSession.split(";");

        if (parts.length == 3) {
            try {
                this.startSession = LocalDateTime.parse(parts[0], FORMATTER);
            } catch (DateTimeParseException e) {
                throw new SleepSessionErrors("Ошибка формата даты, для даты начала сессии сна :" + parts[0]);
            }
            try {
                this.endSession = LocalDateTime.parse(parts[1], FORMATTER);
            } catch (DateTimeParseException e) {
                throw new SleepSessionErrors("Ошибка формата даты, для даты окончания сессии сна :" + parts[1]);
            }
            try {
                this.sleepQuality = SleepQuality.valueOf(parts[2].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new SleepSessionErrors("Ошибка определения качества сна для строки " + parts[2] + ". Доступны значения NORMAL, BAD, GOOD");
            }

            if (this.startSession.isAfter(this.endSession)) {
                throw new SleepSessionErrors("Дата и время начала сна " + this.startSession + " должна быть раньше даты и времени окончания сна " + this.endSession);
            }

            if (Duration.between(this.startSession, this.endSession).toSeconds() >= 24 * 60 * 60) {
                throw new SleepSessionErrors("Период сна " + this.startSession + " - " + this.endSession + " должно быть меньше 24 часов");
            }

        } else {
            throw new SleepSessionErrors("Ошибка создания сессии сна при чтении строки. Некорректный формат для строки " + sleepingSession);
        }
    }

    public static List<SleepingSession> getNightSessions(List<SleepingSession> sessions) {
        return sessions.stream()
                .filter(session -> (
                                (!session.getStartSession().toLocalDate().equals(session.getEndSession().toLocalDate())) ||
                                        (session.getStartSession().toLocalDate().equals(session.getEndSession().toLocalDate()) &&
                                                (session.getStartSession().toLocalTime().isBefore(LocalTime.MIDNIGHT.plusHours(6))))

                        )
                )
                .toList();
    }

    public LocalDateTime getStartSession() {
        return startSession;
    }

    public LocalDateTime getEndSession() {
        return endSession;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SleepingSession that = (SleepingSession) o;

        if (startSession != null ? !startSession.equals(that.startSession) : that.startSession != null) return false;
        if (endSession != null ? !endSession.equals(that.endSession) : that.endSession != null) return false;
        return sleepQuality == that.sleepQuality;
    }

    @Override
    public int hashCode() {
        int result = startSession != null ? startSession.hashCode() : 0;
        result = 31 * result + (endSession != null ? endSession.hashCode() : 0);
        result = 31 * result + (sleepQuality != null ? sleepQuality.hashCode() : 0);
        return result;
    }

}
