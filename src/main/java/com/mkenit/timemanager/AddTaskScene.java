package com.mkenit.timemanager;

import com.mkenit.timemanager.models.tasks.AddTaskModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AddTaskScene implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private DatePicker dateInput;

    @FXML
    private ComboBox<String> durationHour;

    @FXML
    private ComboBox<String> durationMinute;

    @FXML
    private ComboBox<String> importanceInput;

    @FXML
    private TextField nameInput;

    @FXML
    private ComboBox<String> startHour;

    @FXML
    private ComboBox<String> startMinute;
    private AddTaskModel model = new AddTaskModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateInput.getEditor().setDisable(true);
        dateInput.setValue(LocalDate.now());
        nameInput.clear();
        startHour.setItems(FXCollections.observableArrayList(model.getHoursList()));
        startHour.setValue((LocalTime.now().getHour() < 10 ? "0" : "") + LocalTime.now().getHour());
        startMinute.setItems(FXCollections.observableArrayList(model.getMinutesList()));
        startMinute.setValue(String.valueOf(LocalTime.now().getMinute()));
        durationHour.setItems(FXCollections.observableArrayList(model.getHoursList()));
        durationHour.setValue("00");
        durationMinute.setItems(FXCollections.observableArrayList(model.getMinutesList()));
        durationMinute.setValue("15");
        importanceInput.setItems(FXCollections.observableArrayList(model.getPriorityList()));
        importanceInput.setValue("Обычная");
    }

    @FXML
    public void addTaskButtonClicked() {
        model.setName(nameInput.getText());
        model.setStartTime(dateInput.getValue(), startHour.getValue(), startMinute.getValue());
        model.setDuration(durationHour.getValue(), durationMinute.getValue());
        model.setImportance(importanceInput.getValue());

        model.addTask();
        Stage currentStage = (Stage) addButton.getScene().getWindow();
        currentStage.hide();
    }

    @FXML
    public void cancelButtonClicked() {
        Stage currentStage = (Stage) addButton.getScene().getWindow();
        currentStage.hide();
    }

}