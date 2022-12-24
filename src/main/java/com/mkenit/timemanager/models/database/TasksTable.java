package com.mkenit.timemanager.models.database;

import com.mkenit.timemanager.models.tasks.Priority;
import com.mkenit.timemanager.models.tasks.Task;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.Date;

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
                SELECT * FROM tasks WHERE status=FALSE;
                """;
        try {
            connection = ConnectionManager.open();
            Statement statemnet = connection.createStatement();
            ResultSet result = statemnet.executeQuery(sql);
            if (result != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("d-MM-yyyy HH:mm");
                while (result.next()) {
                    String name = result.getString("name");
                    GregorianCalendar startTime = new GregorianCalendar();
                    startTime.setTime(formatter.parse(result.getString("start_time")));
                    Duration duration = Duration.ofMinutes(result.getInt("duration"));
                    Priority priority = Priority.valueOf(result.getString("importance"));
                    boolean finished = result.getBoolean("status");
                    Task tmp = new Task(name, startTime, duration, priority, finished);
                    allTasks.add(tmp);
                }
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
                SELECT * FROM tasks WHERE status=FALSE AND start_time LIKE ?;
                """;
        SimpleDateFormat formatter = new SimpleDateFormat("d-MM-yyyy");
        String todayDate = formatter.format(new Date());
        try {
            connection = ConnectionManager.open();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, todayDate + " %");
            ResultSet result = statement.executeQuery();
            if (result != null) {
                formatter.applyPattern("d-MM-yyyy HH:mm");
                while (result.next()) {
                    String name = result.getString("name");
                    GregorianCalendar startTime = new GregorianCalendar();
                    startTime.setTime(formatter.parse(result.getString("start_time")));
                    Duration duration = Duration.ofMinutes(result.getInt("duration"));
                    Priority priority = Priority.valueOf(result.getString("importance"));
                    boolean finished = result.getBoolean("status");
                    Task tmp = new Task(name, startTime, duration, priority, finished);
                    todayTasks.add(tmp);
                }
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

    public int addTask(Task task) {
        String sql = """
                INSERT INTO TASKS (id, name,start_time,duration,importance, status)
                VALUES(default,?,?,?,?::importance_enum,?);
                """;
        int result = 0;
        try {
            connection = ConnectionManager.open();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.getNameForDB());
            statement.setString(2, task.getStartTimeForDB());
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

    public int updateTaskStatus(Task task) {
        String sql = """
                UPDATE TASKS\s
                SET status=?\s
                WHERE name=?\s
                AND start_time=?\s
                AND duration=?\s
                AND importance=?::importance_enum;\s
                """;
        int result = 0;
        try {
            connection = ConnectionManager.open();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, task.getStatusForDB());
            statement.setString(2, task.getNameForDB());
            statement.setString(3, task.getStartTimeForDB());
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

    public boolean existSameTask(Task task) {
        boolean result = false;
        String sql = """
                SELECT id FROM tasks\s
                WHERE name=?\s
                AND start_time=?\s
                AND duration=?\s
                AND importance=?::importance_enum;\s
                """;
        try {
            connection = ConnectionManager.open();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.getNameForDB());
            statement.setString(2, task.getStartTimeForDB());
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

    public boolean saveChanges(List<Task> changedTasks) {
        boolean result = true;
        try {
            for (Task task : changedTasks)
                if (existSameTask(task))
                    updateTaskStatus(task);
                else
                    addTask(task);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result = false;
        } finally {
            return result;
        }

    }
}