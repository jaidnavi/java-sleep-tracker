package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult {
    private final String analyseText;
    private final String analyseResult;

    public SleepAnalysisResult(String analyseText, String analyseResult) {
        this.analyseText = analyseText;
        this.analyseResult = analyseResult;
    }

    public String getAnalyseResult(){
        return analyseResult;
    }

    @Override
    public String toString() {
        return analyseText + ": " + analyseResult;
    }
}