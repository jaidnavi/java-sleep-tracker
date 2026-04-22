package ru.yandex.practicum.sleeptracker;

public class ReadSleepLogError extends RuntimeException {
    public ReadSleepLogError(String message) {
        super(message);
    }
}
