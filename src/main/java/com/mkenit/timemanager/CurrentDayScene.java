package com.mkenit.timemanager;

import com.mkenit.timemanager.models.Priority;
import com.mkenit.timemanager.models.Task;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
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
        Task task1 = new Task("Доделать КПО", new GregorianCalendar(2022, Calendar.DECEMBER, 25, 8, 50), Duration.ofMinutes(95), Priority.ORDINARY_IMPORTANT);
        ObservableList<Task> list = FXCollections.observableArrayList(task1);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        timeColumnValues();
        durationColumnValues();
        importanceColumnValues();
        statusColumnValues();
        tableOfTasks.setItems(list);
    }

    private void importanceColumnValues(){
        importanceColumn.setCellValueFactory(cellData->{
            Task cellValue=cellData.getValue();
            StringProperty property=new SimpleStringProperty(cellValue.getFormattedImportance());
            return property;
        });
    }

    private void durationColumnValues(){
        durationTaskColumn.setCellValueFactory(cellData->{
            Task cellValue=cellData.getValue();
            StringProperty property=new SimpleStringProperty(cellValue.getFormattedDuration());
            return property;
        });
    }

    private void timeColumnValues(){
        timeColumn.setCellValueFactory(cellData->{
            Task cellValue=cellData.getValue();
            StringProperty property=new SimpleStringProperty(cellValue.getFormattedTime());
            return property;
        });
    }
    private void statusColumnValues() {
        statusColumn.setCellFactory(column -> new CheckBoxTableCell<>());
        statusColumn.setCellValueFactory(cellData -> {
            Task cellValue = cellData.getValue();
            BooleanProperty property = new SimpleBooleanProperty(cellValue.isFinished());
            property.addListener((observable, oldValue, newValue) -> cellValue.setStatus(newValue));
            return property;
        });
    }
}