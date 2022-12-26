package com.mkenit.timemanager.models.tasks;

import com.mkenit.timemanager.models.database.TasksTable;
import com.mkenit.timemanager.models.tasks.Priority;
import com.mkenit.timemanager.models.tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public class AddTaskModel {
    public static final int MAX_HOURS = 24;
    public static final int MAX_MINUTES = 60;
    private LinkedList<String> hoursList;
    private LinkedList<String> minutesList;
    private LinkedList<String> priorityList;

    private String name;
    private GregorianCalendar startTime;
    private Duration duration;
    private Priority importance;
    private boolean status;
    private Task task;


    public AddTaskModel() {
        hoursList = new LinkedList<>();
        minutesList = new LinkedList<>();
        initHoursAndMinutesValues();
        priorityList = new LinkedList<>();
        initPriorityValues();

        name = "Без названия";
        startTime = new GregorianCalendar();
        duration = Duration.ofMinutes(1);
        importance = Priority.ORDINARY_IMPORTANT;
        status = false;
    }

    private void initHoursAndMinutesValues() {
        for (int i = 0; i < MAX_MINUTES; i++)
            if (i < 10) {
                hoursList.add("0" + i);
                minutesList.add("0" + i);
            } else {
                if (i < MAX_HOURS)
                    hoursList.add(String.valueOf(i));
                minutesList.add(String.valueOf(i));
            }
    }

    private void initPriorityValues() {
        priorityList.add("Обычная");
        priorityList.add("Высокая");
        priorityList.add("Низкая");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartTime(LocalDate date, String hour, String minute) {
        try {
            this.startTime.setTime(new SimpleDateFormat("yyyy-MM-d HH:mm")
                    .parse(date.toString() + " " + hour + ":" + minute));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDuration(String hour, String minute) {
        duration = Duration.ofMinutes(hoursToMinutes(Integer.parseInt(hour), Integer.parseInt(minute)));
    }

    public void setImportance(String importance) {
        this.importance = switch (importance) {
            case "Обычная" -> Priority.ORDINARY_IMPORTANT;
            case "Высокая" -> Priority.VERY_IMPORTANT;
            case "Низкая" -> Priority.LOW_IMPORTANT;
            default -> null;
        };
    }

    private void makeTask() {
        task = new Task(name, startTime, duration, importance, status);
    }

    public void addTask() {
        makeTask();
        TasksTable dataBase = new TasksTable();
        dataBase.addTask(task);
    }

    private int hoursToMinutes(int hours, int minutes) {
        return hours * 60 + minutes;
    }

    public LinkedList<String> getHoursList() {
        return hoursList;
    }

    public LinkedList<String> getMinutesList() {
        return minutesList;
    }

    public LinkedList<String> getPriorityList() {
        return priorityList;
    }
}
