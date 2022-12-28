package com.mkenit.timemanager;

import com.mkenit.timemanager.models.database.TasksTable;
import com.mkenit.timemanager.models.tasks.StatusAndDateTasksComparator;
import com.mkenit.timemanager.models.tasks.Task;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class AllTasksScene implements Initializable {
    @FXML
    private Button addButtonTask;

    @FXML
    private TableColumn<Task, String> durationTaskColumn;

    @FXML
    private TableColumn<Task, String> importanceColumn;

    @FXML
    private TableColumn<Task, Boolean> statusColumn;

    @FXML
    private TableView<Task> tableOfTasks;

    @FXML
    private TableColumn<Task, String> nameColumn;

    @FXML
    private TableColumn<Task,String> timeColumn;

    private static TasksTable dataBase=new TasksTable();
    private static ObservableList<Task> listOfTasks;
    private static LinkedList<Task> changedTasks=new LinkedList<>();

    public void appShutdown(){
        saveChangeToDB();
    }

    private void saveChangeToDB() {
        dataBase.saveChanges(changedTasks);
    }

    private Parent loadPage(String pageName) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("templates/" + pageName + ".fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            return root;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableOfTasks.setEditable(true);
        listOfTasks = FXCollections.observableArrayList();

        listOfTasks.addListener(new ListChangeListener<Task>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Task> c) {
                while(c.next()){
                    if(c.wasRemoved())
                        changedTasks.addAll(c.getRemoved());
                }
                tableOfTasks.setItems(listOfTasks);
            }
        });
        addTasksToTable();
        FXCollections.sort(listOfTasks,new StatusAndDateTasksComparator());
    }


    private void addTasksToTable() {
        listOfTasks.addAll(dataBase.getAllTasks());

        nameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        timeColumnValues();
        durationColumnValues();
        importanceColumnValues();
        statusColumnValues();
        tableOfTasks.setItems(listOfTasks);
    }

    private void importanceColumnValues() {
        importanceColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFormattedImportance()));
    }

    private void durationColumnValues() {
        durationTaskColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFormattedDuration()));
    }

    private void timeColumnValues() {
        timeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFormattedDate()));
    }

    private void statusColumnValues() {
        statusColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        statusColumn.setCellValueFactory(cellData -> {
            Task cellValue = cellData.getValue();
            BooleanProperty property = new SimpleBooleanProperty(cellValue.isFinished());
            property.addListener((observable, oldValue, newValue) -> {
                cellValue.setStatus(newValue);
                listOfTasks.remove(cellValue);
                FXCollections.sort(listOfTasks,new StatusAndDateTasksComparator());
            });
            return property;
        });
    }

    @FXML
    public void addTaskButtonClicked(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("templates/AddTask.fxml"));
        Parent root= null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root, 752, 540);
        Stage stage=new Stage();
        stage.setTitle("Добавить задачу");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setOnHidden(windowEvent -> {
            listOfTasks.clear();
            listOfTasks.addAll(dataBase.getAllTasks());
            FXCollections.sort(listOfTasks,new StatusAndDateTasksComparator());
        });
        stage.show();
    }

    public static void updateTasks(){
        listOfTasks.clear();
        listOfTasks.addAll(dataBase.getAllTasks());
    }

}
