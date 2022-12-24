package com.mkenit.timemanager.models.tasks;

import java.util.Comparator;

public class StatusAndDateTasksComparator implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
        int resultOfComparison = 0;
        if (o1 == null && o2 == null)
            resultOfComparison = 0;
        else if (o1 == null)
            resultOfComparison = -1;
        else if (o2 == null)
            resultOfComparison = 1;
        else if (o1.isFinished() == o2.isFinished())
            resultOfComparison = o1.getStartTime().compareTo(o2.getStartTime());
        else if (o1.isFinished())
            resultOfComparison = -1;
        else if (o2.isFinished())
            resultOfComparison = 1;
        return resultOfComparison;
    }
}
