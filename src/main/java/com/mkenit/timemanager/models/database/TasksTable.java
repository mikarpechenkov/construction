package com.mkenit.timemanager.models.database;

import com.mkenit.timemanager.models.tasks.Priority;
import com.mkenit.timemanager.models.tasks.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class TasksTable {
    private Connection connection;

    private LinkedList<Task> todayTasks = new LinkedList<>();

    private LinkedList<Task> allTasks = new LinkedList<>();

    public LinkedList<Task> getTodayTasks() {
        todayTasks.clear();
        loadTodayTasks();
        return todayTasks;
    }

    public LinkedList<Task> getAllTasks() {
        allTasks.clear();
        loadAllTasks();
        return allTasks;
    }

    private void loadAllTasks() {
        String sql = """
                SELECT * FROM TASKS WHERE status=FALSE;
                """;
        try {
            connection = ConnectionManager.open();
            Statement statemnet = connection.createStatement();
            ResultSet result = statemnet.executeQuery(sql);
            while (result.next()) {
                String name = result.getString("name");
                GregorianCalendar startTime = new GregorianCalendar();
                startTime.setTime(result.getTimestamp("start_time"));
                Duration duration = Duration.ofMinutes(result.getInt("duration"));
                Priority priority = Priority.valueOf(result.getString("importance"));
                boolean finished = result.getBoolean("status");
                Task tmp = new Task(name, startTime, duration, priority, finished);
                allTasks.add(tmp);
            }
            result.close();//Добавить обработку ошибок
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception ignore) {
            }
        }
    }

    private void loadTodayTasks() {
        String sql = """
                SELECT * FROM tasks WHERE status=FALSE AND FORMATDATETIME(START_TIME,'yyyy-MM-d') = ?;
                """;
        try {
            connection = ConnectionManager.open();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String name = result.getString("name");
                GregorianCalendar startTime = new GregorianCalendar();
                startTime.setTime(result.getTimestamp("start_time")); //проверить, работает ли
                Duration duration = Duration.ofMinutes(result.getInt("duration"));
                Priority priority = Priority.valueOf(result.getString("importance"));
                boolean finished = result.getBoolean("status");
                Task tmp = new Task(name, startTime, duration, priority, finished);
                todayTasks.add(tmp);
            }
            result.close();//Добавить обработку ошибок
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception ignore) {
            }
        }
    }

    private int addTask(Task task) {
        String sql = """
                INSERT INTO TASKS (id, name,start_time,duration,importance, status)
                VALUES(default,?,?,?,?,?);
                """;
        int result = 0;
        try {
            connection = ConnectionManager.open();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.getNameForDB());
            statement.setTimestamp(2, task.getStartTimeForDB());
            statement.setInt(3, task.getDurationForDB());
            statement.setString(4, task.getImportanceForDB());
            statement.setBoolean(5, task.getStatusForDB());
            result = statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception ignore) {
            } finally {
                return result;
            }
        }
    }

    private int updateTaskStatus(Task task) {
        String sql = """
                UPDATE TASKS\s
                SET status=?\s
                WHERE name=?\s
                AND start_time=?\s
                AND duration=?\s
                AND importance=?;\s
                """;
        int result = 0;
        try {
            connection = ConnectionManager.open();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, task.getStatusForDB());
            statement.setString(2, task.getNameForDB());
            statement.setTimestamp(3, task.getStartTimeForDB());
            statement.setInt(4, task.getDurationForDB());
            statement.setString(5, task.getImportanceForDB());
            result = statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception ignore) {
            } finally {
                return result;
            }
        }
    }

    private boolean existSameTask(Task task) {
        boolean result = false;
        String sql = """
                SELECT id FROM tasks\s
                WHERE name=?\s
                AND start_time=?\s
                AND duration=?\s
                AND importance=?;\s
                """;
        try {
            connection = ConnectionManager.open();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.getNameForDB());
            statement.setTimestamp(2, task.getStartTimeForDB());
            statement.setInt(3, task.getDurationForDB());
            statement.setString(4, task.getImportanceForDB());
            result = statement.executeQuery().next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception ignore) {
            } finally {
                return result;
            }
        }
    }

    public void saveChanges(List<Task> changedTasks) {
        for (Task task : changedTasks)
            if (existSameTask(task)) updateTaskStatus(task);
            else {
                addTask(task);
            }
    }

    public void saveChanges(Task changedTask) {
        addTask(changedTask);
    }

}