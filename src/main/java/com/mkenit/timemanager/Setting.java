package com.mkenit.timemanager;
public class Setting {
    private boolean notification;
    private String studyGroup;

    public boolean isVisibilityStudySchedule() {
        return visibilityStudySchedule;
    }

    public void setVisibilityStudySchedule(boolean visibilityStudySchedule) {
        this.visibilityStudySchedule = visibilityStudySchedule;
    }

    private boolean visibilityStudySchedule;

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public String getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(String studyGroup) {
        this.studyGroup = studyGroup;
    }
}
