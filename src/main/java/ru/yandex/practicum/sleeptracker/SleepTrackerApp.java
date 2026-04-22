package ru.yandex.practicum.sleeptracker;

import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import java.util.Objects;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.function.Function;
import java.util.ArrayList;

public class SleepTrackerApp {

    private static final String SLEEP_LOG_FILE = "sleep_log.txt";
    private static List<Function<List<SleepingSession>, SleepAnalysisResult>> analyticsFunctions = new ArrayList<>();

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                throw new ReadSleepLogError("Укажите путь к файлу с логом сна в аргументе командной строки.");
            }

            Path path = Paths.get(args[0]);

            if (!Files.exists(path)) {
                throw new ReadSleepLogError("Указанная директория не существует.");
            }

            Path logFilePath = path.resolve(SLEEP_LOG_FILE);

            if (!Files.exists(logFilePath)) {
                throw new ReadSleepLogError("Файла лога сна " + logFilePath + " не существует в директории " + path);
            }

            if (!Files.isRegularFile(logFilePath)) {
                throw new ReadSleepLogError("Лог сна " + logFilePath + " должен быть файлом.");
            }

            List<SleepingSession> sleepingSessions;

            try (BufferedReader br = new BufferedReader(new FileReader(logFilePath.toFile(), StandardCharsets.UTF_8))) {
                sleepingSessions = br.lines().filter(line -> !line.isBlank()).map(line -> {
                    try {
                        return new SleepingSession(line);
                    } catch (SleepSessionErrors exception) {
                        System.out.println(exception.getMessage());
                        return null;
                    }
                }).filter(Objects::nonNull).toList();
            } catch (IOException e) {
                throw new ReadSleepLogError("Ошибка при чтении файла лога сна: " + e.getMessage());
            }

            if (sleepingSessions.isEmpty()) {
                throw new ReadSleepLogError("Файл лога сна не содержит строк сессий сна");
            }

            analyticsFunctions.add(new CountSessionsFunction());
            analyticsFunctions.add(new MinDurationFunction());
            analyticsFunctions.add(new MaxDurationFunction());
            analyticsFunctions.add(new AvgDurationFunction());

            analyticsFunctions.stream()
                    .map(function -> function.apply(sleepingSessions)) // Выполняем расчёт
                    .forEach(System.out::println);

        } catch (ReadSleepLogError exception) {
            System.out.println(exception.getMessage());
        }
    }
}