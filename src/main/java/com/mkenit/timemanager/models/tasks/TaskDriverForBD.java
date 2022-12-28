package com.mkenit.timemanager.models.tasks;

import java.sql.Timestamp;

public interface TaskDriverForBD {
    String getNameForDB();
    Timestamp getStartTimeForDB();
    int getDurationForDB();
    String getImportanceForDB();
    boolean getStatusForDB();
}
