package com.mkenit.timemanager;

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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class TimerScene implements Initializable {

    public static final int MAX_MINUTES = 180;

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

    @FXML
    private Button timerMenuItem;


    private TreeMap<Integer, String> timerDisplayMap;
    private int currentSecond;

    private Timeline timerTimeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> minutesList = FXCollections.observableArrayList();
        ObservableList<String> secondsList = FXCollections.observableArrayList();

        //Заполняем наши листы для комбобоксов значениями
        for (int i = 0; i <= MAX_MINUTES; i++) {
            if (i <= 9){
                minutesList.add("0" + i);
                secondsList.add("0"+i);
            }else {
                if(i<=60)
                    secondsList.add(String.valueOf(i));
                minutesList.add(String.valueOf(i));
            }
        }
        //Добавляем пункты в комбобоксы
        minutesInput.setItems(minutesList);
        minutesInput.setValue("00");
        secondsInput.setItems(secondsList);
        secondsInput.setValue("00");

        //Делаем числовое представление красивым
        timerDisplayMap = new TreeMap<>();
        for (int i = 0; i <= MAX_MINUTES; i++)
            if (i >= 0 && i < 10)
                timerDisplayMap.put(i, "0" + i);
            else
                timerDisplayMap.put(i, String.valueOf(i));

    }

    public void startButtonClick() {
        currentSecond = minutesToSeconds(Integer.parseInt(minutesInput.getValue()),
                Integer.parseInt(secondsInput.getValue()));
        if (currentSecond != 0)
            scrollUp();
        try {
            startCountdown();
        } catch (Exception ex) {
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
                setTimerValues();
                currentSecond--;
            } catch (Exception ex) {
            }
        }), new KeyFrame(Duration.seconds(1)));
        timerTimeline.setCycleCount(currentSecond);
        timerTimeline.play();
        timerTimeline.setOnFinished(event -> scrollDown());
    }


    private void setTimerValues() {
        HashMap<String, Integer> minutesAndSeconds = secondsToMinutes(currentSecond);
        outputSeconds.setText(timerDisplayMap.get(minutesAndSeconds.get("seconds")));
        outputMinutes.setText(timerDisplayMap.get(minutesAndSeconds.get("minutes")));
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

    private int minutesToSeconds(int minutes, int seconds) {
        return minutes * 60 + seconds;
    }

    private HashMap<String, Integer> secondsToMinutes(int currentSecond) {
        int minutes = currentSecond / 60;
        currentSecond = currentSecond % 60;
        HashMap<String, Integer> minutesAndSeconds = new HashMap<>();
        minutesAndSeconds.put("minutes", minutes);
        minutesAndSeconds.put("seconds", currentSecond);
        return minutesAndSeconds;
    }
}
