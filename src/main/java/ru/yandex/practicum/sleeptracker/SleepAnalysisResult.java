package ru.yandex.practicum.sleeptracker;

import java.util.Objects;

public class SleepAnalysisResult {
    private final String analyseText;
    private final String analyseResult;

    public SleepAnalysisResult(String analyseText, String analyseResult) {
        this.analyseText = analyseText;
        this.analyseResult = analyseResult;
    }

    public String getAnalyseResult() {
        return analyseResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SleepAnalysisResult that = (SleepAnalysisResult) o;
        return Objects.equals(analyseText, that.analyseText) &&
                Objects.equals(analyseResult, that.analyseResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(analyseText, analyseResult);
    }


    @Override
    public String toString() {
        return analyseText + ": " + analyseResult;
    }
}