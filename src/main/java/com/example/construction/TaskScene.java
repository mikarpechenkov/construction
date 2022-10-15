import java.util.ArrayList;

public class TaskScene implements TaskCreator {
    public static void printAllTasks(){}

    public static void hideTasks(ArrayList<Task> hiddenTasks){}

    public static void completeTask(Task task){}

    public static Task createTask(){
        return new Task();
    }
    public static Task editTask(Task task){
        return new Task();
    }

}
