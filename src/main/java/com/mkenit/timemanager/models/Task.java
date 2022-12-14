package com.mkenit.timemanager.models;

import javafx.util.Duration;
import java.util.GregorianCalendar;

public class Task {
    String name;
    GregorianCalendar startTime;
    Duration duration;
    Priority importance;
    boolean status;


    public Task(String name, GregorianCalendar startTime, Duration duration, Priority importance) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.importance = importance;
        status=false;
    }

    public Task() {
        name = "Без названия";
        startTime = new GregorianCalendar();
        duration = Duration.minutes(1);
        importance = Priority.ORDINARY_IMPORTANT;
        status = false;
    }

}
