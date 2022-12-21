package com.mkenit.timemanager.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.GregorianCalendar;

public class Task {
    private String name;
    private GregorianCalendar startTime;
    private Duration duration;
    private Priority importance;
    private boolean finished;

    public Task(String name, GregorianCalendar startTime, Duration duration, Priority importance) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.importance = importance;
        finished = false;
    }

    public Task() {
        name = "Без названия";
        startTime = new GregorianCalendar();
        duration = Duration.minutes(1);
        importance = Priority.ORDINARY_IMPORTANT;
        finished = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GregorianCalendar getStartTime() {
        return startTime;
    }

    public void setStartTime(GregorianCalendar startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Priority getImportance() {
        return importance;
    }

    public void setImportance(Priority importance) {
        this.importance = importance;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setStatus(boolean finished) {
        this.finished = finished;
    }
}
