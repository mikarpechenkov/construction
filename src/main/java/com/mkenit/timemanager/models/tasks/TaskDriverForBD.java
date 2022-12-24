package com.mkenit.timemanager.models.tasks;

public interface TaskDriverForBD {
    String getNameForDB();
    String getStartTimeForDB();
    int getDurationForDB();
    String getImportanceForDB();
    boolean getStatusForDB();
}
