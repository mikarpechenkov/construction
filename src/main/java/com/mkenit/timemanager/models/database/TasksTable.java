package com.mkenit.timemanager.models.database;

import com.mkenit.timemanager.models.Priority;
import com.mkenit.timemanager.models.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class TasksTable {
    private Connection connection;

    private LinkedList<Task> todayTasks=new LinkedList<>();

    public LinkedList<Task> getTodayTasks() {
        loadTodayTasks();
        return todayTasks;
    }

    private void loadTodayTasks() {
        String sql = """
                SELECT * FROM tasks;
                """;
        try {
            connection = ConnectionManager.open();
            Statement statemnet = connection.createStatement();
            ResultSet result = statemnet.executeQuery(sql);
            while (result.next()){
                String name=result.getString("name");
                GregorianCalendar startTime=new GregorianCalendar();
                startTime.setTime(result.getDate("start_time"));
                Duration duration=Duration.ofMinutes(result.getInt("duration"));
                Priority priority=Priority.valueOf(result.getString("importance"));
                boolean finished=result.getBoolean("status");
                Task tmp=new Task(name,startTime,duration,priority,finished);
                todayTasks.add(tmp);
            }
            result.close();//Добавить обработку ошибок
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            }catch(Exception ignore){}
        }
    }

}

class Main{
    public static void main(String[] args) {
        TasksTable tasksTable=new TasksTable();
        System.out.println(tasksTable.getTodayTasks());
    }
}
