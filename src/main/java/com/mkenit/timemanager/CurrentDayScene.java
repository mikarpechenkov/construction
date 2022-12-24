package com.mkenit.timemanager;

import com.mkenit.timemanager.models.database.TasksTable;
import com.mkenit.timemanager.models.tasks.Priority;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class CurrentDayScene implements Initializable {
    @FXML
    private Button AddTask;
    @FXML
    private Button allTasksMenuItem;
    @FXML
    private Button currentDayMenuItem;
    @FXML
    private TableColumn<Task, String> durationTaskColumn;
    @FXML
    private TableColumn<Task, String> importanceColumn;
    @FXML
    private Button settingsMenuItem;
    @FXML
    private TableColumn<Task, Boolean> statusColumn;
    @FXML
    private TableView<Task> tableOfTasks;
    @FXML
    private TableColumn<Task, String> nameColumn;
    @FXML
    private TableColumn<Task, String> timeColumn;
    @FXML
    private Button timerMenuItem;
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane currentDayContent;
    private Parent allTasksContent;
    private Parent timerContent;
    private Parent settingsContent;
    private TasksTable dataBase=new TasksTable();
    private ObservableList<Task> listOfTasks;

    private LinkedList<Task> changedTasks=new LinkedList<>();

    @FXML
    private void selectAllTasksPage() {
        if (allTasksContent != null)
            borderPane.setCenter(allTasksContent);
    }

    @FXML
    private void selectCurrentDayPage() {
        borderPane.setCenter(currentDayContent);
    }

    @FXML
    private void selectTimerPage() {
        if (timerContent != null)
            borderPane.setCenter(timerContent);
    }

    @FXML
    private void selectSettingsPage() {
        if (settingsContent != null)
            borderPane.setCenter(settingsContent);
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
        allTasksContent = loadPage("AllTasks");
        timerContent = loadPage("Timer");
        settingsContent = loadPage("Settings");
        tableOfTasks.setEditable(true);
        listOfTasks = FXCollections.observableArrayList();

        listOfTasks.addListener(new ListChangeListener<Task>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Task> c) {
                tableOfTasks.setItems(listOfTasks);
            }
        });

        addTasksToTable();
        FXCollections.sort(listOfTasks,new StatusAndDateTasksComparator());
    }


    private void addTasksToTable() {
        listOfTasks.addAll(dataBase.getTodayTasks());
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
                new SimpleStringProperty(cellData.getValue().getFormattedTime()));
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
}