package com.mkenit.timemanager.models;

import java.util.LinkedList;
import java.util.TreeMap;

public class TimerModel {
    public static final int MAX_MINUTES = 180;
    public static final int MAX_SECONDS = 60;
    private TreeMap<Integer, String> timerDisplayMap;
    private LinkedList<String> minutesList;
    private LinkedList<String> secondsList;
    private String viewOfPresentSeconds;
    private String viewOfPresentMinutes;
    private int currentSeconds;

    public TimerModel() {
        minutesList = new LinkedList<>();
        secondsList = new LinkedList<>();
        timerDisplayMap = new TreeMap<>();
        initMinutesAndSecondsValues();
        viewOfPresentSeconds = "00";
        viewOfPresentMinutes = "00";
    }

    private void initMinutesAndSecondsValues() {
        for (int i = 0; i <= TimerModel.MAX_MINUTES; i++)
            if (i >= 0 && i < 10)
                timerDisplayMap.put(i, "0" + i);
            else
                timerDisplayMap.put(i, String.valueOf(i));

        minutesList.addAll(timerDisplayMap.values());
        timerDisplayMap.entrySet().stream()
                .filter(entry -> entry.getKey() <= 60)
                .forEach(entry -> secondsList.add(entry.getValue()));
    }

    public LinkedList<String> getMinutesList() {
        return minutesList;
    }

    public LinkedList<String> getSecondsList() {
        return secondsList;
    }

    public String getViewOfPresentSeconds() {
        return viewOfPresentSeconds;
    }

    public String getViewOfPresentMinutes() {
        return viewOfPresentMinutes;
    }

    public int getCurrentSeconds() {
        return currentSeconds;
    }

    public void setCurrentSeconds(int minutes, int seconds) {
        currentSeconds = minutesToSeconds(minutes, seconds);
    }

    public void setCurrentSeconds(int currentSeconds) {
        if (currentSeconds < 0)
            throw new IllegalArgumentException("Попытка задать отрицательное время");
        this.currentSeconds = currentSeconds;
    }

    public static int minutesToSeconds(int minutes, int seconds) {
        return minutes * 60 + seconds;
    }

    private void secondsToMinutes(int currentSeconds) {
        int minutes = currentSeconds / 60;
        currentSeconds = currentSeconds % 60;
        viewOfPresentSeconds = timerDisplayMap.get(currentSeconds);
        viewOfPresentMinutes = timerDisplayMap.get(minutes);
    }

    public void countOneSecond() {
        if (currentSeconds < 0)
            throw new IllegalArgumentException("Попытка задать отрицательное время");
        if (currentSeconds != 0)
            currentSeconds--;
        secondsToMinutes(currentSeconds);
    }
}
