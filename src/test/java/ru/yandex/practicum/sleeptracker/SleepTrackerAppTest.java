package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

public class SleepTrackerAppTest {
    @Test
    void mainTestRunNoCrash() {
        SleepTrackerApp.main(new String[]{System.getProperty("user.dir") + "\\src\\main\\resources"});
    }

    @Test
    void mainTestFileErrNoCrash() {
        SleepTrackerApp.main(new String[]{""});
    }
}