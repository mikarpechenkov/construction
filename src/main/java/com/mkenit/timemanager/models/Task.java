package com.mkenit.timemanager.models;

import javafx.util.Duration;
import java.util.GregorianCalendar;

public class Task {
    String name="Без названия";
    GregorianCalendar startTime=new GregorianCalendar();
    Duration duration=Duration.minutes(1);
    Priority importance=Priority.ORDINARY_IMPORTANT;
    boolean status=false;

    public Task(String name, GregorianCalendar startTime, Duration duration, Priority importance) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.importance = importance;
    }

    public Task() {}

}
