package com.mkenit.timemanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CurrentDayScene implements Initializable {
    @FXML
    private Button AddTask;

    @FXML
    private Button allTasksMenuItem;

    @FXML
    private Button currentDayMenuItem;

    @FXML
    private TableColumn<?, ?> durationTask;

    @FXML
    private TableColumn<?, ?> importanceColumn;

    @FXML
    private Button settingsMenuItem;

    @FXML
    private TableColumn<?, ?> statusColumn;

    @FXML
    private TableView<?> tableOfTasks;

    @FXML
    private TableColumn<?, ?> taskColumn;

    @FXML
    private TableColumn<?, ?> timeColumn;

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
    }
}