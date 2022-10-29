package com.mkenit.timemanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CurrentDayController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}