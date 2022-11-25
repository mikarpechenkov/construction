package com.mkenit.timemanager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimerScene extends Timer {
    @FXML
    public Button CancelButton;

    @FXML
    public Button SettingTimerButton;

    @FXML
    public Label TimerHeader;
    @FXML
    public Button StartButton;

    @FXML
    public Label TimerLabel;

    public void clickOnStartButton() {
        TimerHeader.setText("Таймер запустился!");
    }

    public static void printTimer(Timer timer) {
    }
}
