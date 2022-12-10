package com.mkenit.timemanager;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
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
    private ComboBox<Integer> minutesInput;

    @FXML
    private Label outputMinutes;

    @FXML
    private Label outputSeconds;

    @FXML
    private ComboBox<Integer> secondsInput;

    @FXML
    private AnchorPane setupTimerPane;

    @FXML
    private Button startButton;

    @FXML
    private Button timerMenuItem;

    private TreeMap<Integer, String> timerDisplayMap;
    private int currentSecond;

    private Thread timerThread;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> minutesList = FXCollections.observableArrayList();
        ObservableList<Integer> secondsList = FXCollections.observableArrayList();
        //Заполняем наши листы для комбобоксов значениями
        for (int i = 0; i <= 180; i++) {
            if (i >= 0 && i <= 60)
                secondsList.add(i);
            minutesList.add(i);
        }
        //Добавляем пункты в комбобоксы
        minutesInput.setItems(minutesList);
        minutesInput.setValue(0);
        secondsInput.setItems(secondsList);
        secondsInput.setValue(0);

        //Делаем числовое представление красивым
        timerDisplayMap = new TreeMap<>();
        for (int i = 0; i <= 60; i++)
            if (i >= 0 && i < 10)
                timerDisplayMap.put(i, "0" + i);
            else
                timerDisplayMap.put(i,String.valueOf(i));

    }

    public void startButtonClick() {
        currentSecond=minutesToSeconds(minutesInput.getValue(),secondsInput.getValue());
        minutesInput.setValue(0);
        secondsInput.setValue(0);
        scrollUp();
        try {
            startCountdown();
        }catch (Exception ex){ }
    }

    public void cancelButtonClick() {
        try {
            timerThread.interrupt();
        }
        catch (Exception ex){}
        scrollDown();
    }

    private void startCountdown(){
        timerThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (currentSecond!=0){
                        setTimerValues();
                        Thread.sleep(1000);
                        currentSecond--;
                    }
                    scrollDown();
                    timerThread.interrupt();
                }catch (Exception ex){  }
            }
        });
        timerThread.start();
    }

    private void setTimerValues() {
        HashMap minutesAndSeconds=secondsToMinutes(currentSecond);
        System.out.println(minutesAndSeconds.get("minutes")+"-"+timerDisplayMap.get(minutesAndSeconds.get("seconds")));
        System.out.println(outputMinutes.getText());
//        outputMinutes.setText(minutesAndSeconds.get("minutes").toString());
//        outputSeconds.setText(timerDisplayMap.get(minutesAndSeconds.get("seconds")));
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

    private int minutesToSeconds(int minutes, int seconds){
        return minutes*60+seconds;
    }

    private HashMap<String,Integer> secondsToMinutes(int currentSecond){
        int minutes=currentSecond/60;
        int second=currentSecond%60;
        HashMap<String,Integer> minutesAndSeconds=new HashMap<>();
        minutesAndSeconds.put("minutes",minutes);
        minutesAndSeconds.put("seconds",currentSecond);
        return  minutesAndSeconds;
    }
}
