package com.mkenit.timemanager;

import com.mkenit.timemanager.models.Priority;
import com.mkenit.timemanager.models.Task;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class CurrentDayScene implements Initializable {
    @FXML
    private Button AddTask;
    @FXML
    private Button allTasksMenuItem;
    @FXML
    private Button currentDayMenuItem;
    @FXML
    private TableColumn<Task, Integer> durationTaskColumn;
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
        //Потом убрать
        addTasks();
    }

    private void addTasks() {
        Task task1 = new Task("Доделать КПО", new GregorianCalendar(2020, Calendar.DECEMBER, 15, 20, 50), Duration.minutes(45), Priority.ORDINARY_IMPORTANT);
        ObservableList<Task> list = FXCollections.observableArrayList(task1);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("startTime"));
        durationTaskColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("duration"));
        importanceColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("importance"));
        statusColumnFactory();
        tableOfTasks.setItems(list);
    }

    private void statusColumnFactory() {
        statusColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        statusColumn.setCellValueFactory(cellData -> {
            Task cellValue = cellData.getValue();
            BooleanProperty property = cellValue.isFinished();

            // Add listener to handler change
            property.addListener((observable, oldValue, newValue) -> cellValue.setStatus(newValue));

            return property;
        });
    }
}