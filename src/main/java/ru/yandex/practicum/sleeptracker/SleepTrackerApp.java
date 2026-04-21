package ru.yandex.practicum.sleeptracker;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class SleepTrackerApp {

    private static final String SLEEP_LOG_FILE = "sleep_log.txt";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите путь к файлу с логом сна.");
            return;
        }

        Path path = Paths.get(args[0]);

        if (!Files.exists(path)) {
            System.out.println("Указанная директория не существует.");
            return;
        }

        Path logFilePath = path.resolve(SLEEP_LOG_FILE);

        if (!Files.exists(logFilePath)) {
            System.out.println("Файла лога сна не существует в директории " + path);
            return;
        }

        List<SleepingSession> sleepingSessions;

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath.toFile(), StandardCharsets.UTF_8))) {
            List<String> stringSleepingSessions = br.lines().filter(line -> !line.isBlank()).collect(Collectors.toList());

            sleepingSessions = stringSleepingSessions.stream().map(line -> new SleepingSession(line)).collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }


        if (!sleepingSessions.isEmpty()) {
            System.out.println("Всего обработано сессий: " + sleepingSessions.size());

            sleepingSessions.forEach(session -> {
                if (session.getStartSession() != null && session.getEndSession() != null) {
                    Duration duration = Duration.between(
                            session.getStartSession(),
                            session.getEndSession()
                    );

                    long hours = duration.toHours();
                    long minutes = duration.toMinutesPart();

                    System.out.printf("Сон с %s до %s длился %d ч. %d мин. Качество: %s%n",
                            session.getStartSession().format(DateTimeFormatter.ofPattern("dd.MM HH:mm")),
                            session.getEndSession().format(DateTimeFormatter.ofPattern("dd.MM HH:mm")),
                            hours, minutes, session.getSleepQuality());
                }
            });
        }


    }
}