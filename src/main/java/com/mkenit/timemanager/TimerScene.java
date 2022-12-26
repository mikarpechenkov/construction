package com.mkenit.timemanager;

import com.mkenit.timemanager.models.TimerModel;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class TimerScene implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane displayTimerPane;

    @FXML
    private ComboBox<String> minutesInput;

    @FXML
    private Label outputMinutes;

    @FXML
    private Label outputSeconds;

    @FXML
    private ComboBox<String> secondsInput;

    @FXML
    private AnchorPane setupTimerPane;

    @FXML
    private Button startButton;

    private Timeline timerTimeline;

    private TimerModel timerModel = new TimerModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Добавляем пункты в комбобоксы
        minutesInput.setItems(FXCollections.observableArrayList(timerModel.getMinutesList()));
        minutesInput.setValue(timerModel.getViewOfPresentMinutes());
        secondsInput.setItems(FXCollections.observableArrayList(timerModel.getSecondsList()));
        secondsInput.setValue(timerModel.getViewOfPresentSeconds());
    }

    public void startButtonClick() {
        timerModel.setCurrentSeconds(Integer.parseInt(minutesInput.getValue()),
                Integer.parseInt(secondsInput.getValue()));
        if (timerModel.getCurrentSeconds() != 0) {
            scrollUp();
            try {
                startCountdown();
            } catch (Exception ex) {
            }
        }
    }

    public void cancelButtonClick() {
        try {
            timerTimeline.stop();
        } catch (Exception ex) {
        }
        scrollDown();
    }

    private void startCountdown() {
        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(0), event -> {
            try {
                timerModel.countOneSecond();
                setTimerValues();
            } catch (Exception ex) {
            }
        }), new KeyFrame(Duration.seconds(1)));
        timerTimeline.setCycleCount(timerModel.getCurrentSeconds());
        timerTimeline.play();
        timerTimeline.setOnFinished(event -> scrollDown());
    }


    private void setTimerValues() {
        outputSeconds.setText(timerModel.getViewOfPresentSeconds());
        outputMinutes.setText(timerModel.getViewOfPresentMinutes());
    }

    //Анимация для смены Пэйнов таймера
    private void scrollUp() {
        TranslateTransition transition1 = new TranslateTransition();
        transition1.setDuration(Duration.millis(100));
        transition1.setToX(0);
        transition1.setToY(-402);
        transition1.setNode(setupTimerPane);

        TranslateTransition transition2 = new TranslateTransition();
        transition2.setDuration(Duration.millis(100));
        transition2.setFromX(0);
        transition2.setFromY(402);
        transition2.setToX(0);
        transition2.setToY(0);
        transition2.setNode(displayTimerPane);

        ParallelTransition parallelTransition = new ParallelTransition(transition1, transition2);
        parallelTransition.play();
    }

    private void scrollDown() {
        TranslateTransition transition1 = new TranslateTransition();
        transition1.setDuration(Duration.millis(100));
        transition1.setToX(0);
        transition1.setToY(402);
        transition1.setNode(displayTimerPane);

        TranslateTransition transition2 = new TranslateTransition();
        transition2.setDuration(Duration.millis(100));
        transition2.setFromX(0);
        transition2.setFromY(-402);
        transition2.setToX(0);
        transition2.setToY(0);
        transition2.setNode(setupTimerPane);

        ParallelTransition parallelTransition = new ParallelTransition(transition1, transition2);
        parallelTransition.play();
    }
}
