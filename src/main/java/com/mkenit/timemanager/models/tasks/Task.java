package com.mkenit.timemanager.models.tasks;

import com.mkenit.timemanager.models.tasks.Priority;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Task implements TaskDriverForBD{
    private String name;
    private GregorianCalendar startTime;
    private Duration duration;
    private Priority importance;
    private boolean finished;

    public Task(String name, GregorianCalendar startTime, Duration duration, Priority importance, boolean finished) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.importance = importance;
        this.finished = finished;
    }

    public Task() {
        name = "Без названия";
        startTime = new GregorianCalendar();
        duration = Duration.ofMinutes(1);
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

    public Duration getDuration(){return duration;}

    @Override
    public String getNameForDB() {
        return name;
    }

    @Override
    public Timestamp getStartTimeForDB() {
        return new Timestamp(startTime.getTimeInMillis());
    }

    @Override
    public int getDurationForDB(){
        return (int)duration.toMinutes();
    }
    @Override
    public String getImportanceForDB(){
        return importance.toString();
    }

    @Override
    public boolean getStatusForDB(){return finished;}
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

    public String getFormattedTime() {
        return new SimpleDateFormat("hh:mm a").format(startTime.getTime());
    }

    public String getFormattedDate() {
        GregorianCalendar now = new GregorianCalendar();
        SimpleDateFormat formattedDate = new SimpleDateFormat();

        int dayDuration = (int) now.toZonedDateTime().toLocalDate().
                until(startTime.toZonedDateTime(), ChronoUnit.DAYS);

        switch (dayDuration) {
            case -2 -> formattedDate.applyLocalizedPattern("Позавчера hh:mm a");
            case -1 -> formattedDate.applyLocalizedPattern("Вчера hh:mm a");
            case 0 -> formattedDate.applyLocalizedPattern("Сегодня hh:mm a");
            case 1 -> formattedDate.applyLocalizedPattern("Завтра hh:mm a");
            case 2 -> formattedDate.applyLocalizedPattern("Послезавтра hh:mm a");
            default -> {
                if (startTime.get(Calendar.YEAR) == now.get(Calendar.YEAR))
                    if (startTime.get(Calendar.WEEK_OF_YEAR) == now.get(Calendar.WEEK_OF_YEAR))
                        formattedDate.applyLocalizedPattern("EEEE hh:mm a");
                    else
                        formattedDate.applyLocalizedPattern("d MMM hh:mm a");
                else
                    formattedDate.applyLocalizedPattern("d/MM/y hh:mm a");
            }
        }

        return formattedDate.format(startTime.getTime());
    }

    public String getFormattedDuration(){
        if(duration.toHours()>0)
            return duration.toHours()+ " ч "+duration.toMinutesPart()+" мин";
        else
            return duration.toMinutes()+" мин";
    }

    public String getFormattedImportance(){
        return switch (importance){
            case ORDINARY_IMPORTANT -> "Обычная";
            case LOW_IMPORTANT -> "Низкая";
            case VERY_IMPORTANT -> "Высокая";
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (finished != task.finished) return false;
        if (!name.equals(task.name)) return false;
        if (!startTime.equals(task.startTime)) return false;
        if (!duration.equals(task.duration)) return false;
        return importance == task.importance;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + startTime.hashCode();
        result = 31 * result + duration.hashCode();
        result = 31 * result + importance.hashCode();
        result = 31 * result + (finished ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", startTime= " + getFormattedDate() +
                ", duration=" + duration.toMinutes()+
                ", importance=" + importance +
                ", finished=" + finished +
                '}';
    }
}
