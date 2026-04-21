package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class SleepingSession {
    private LocalDateTime startSession;
    private LocalDateTime endSession;
    private SleepQuality sleepQuality;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");


    public SleepingSession(String sleepingSession){
        String[] parts = sleepingSession.split(";");

        if (parts.length == 3) {
            try {
                this.startSession = LocalDateTime.parse(parts[0], FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка формата даты, для даты начала сессии сна :" + parts[0]);
            }
            try {
                this.endSession = LocalDateTime.parse(parts[1], FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка формата даты, для даты окончания сессии сна :" + parts[1]);
            }

            try {
                this.sleepQuality = SleepQuality.valueOf(parts[2].toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка определения качества сна для строки " + parts[2] + ". Доступны значения NORMAL, BAD, GOOD");
            }

        } else {
            System.out.println("Ошибка создания сессии сна при чтении строки. Некорректный формат для строки " + sleepingSession);
        }
    }

    public LocalDateTime getStartSession() { return startSession; }
    public LocalDateTime getEndSession() { return endSession; }
    public SleepQuality getSleepQuality() { return sleepQuality; }

}
