package com.mkenit.timemanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class CurrentDayTest extends ApplicationTest{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("templates\\CurrentDay.fxml"));
        Scene scene=new Scene(root);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("css\\main-page-style.css")));
        stage.setScene(scene);
        stage.show();
    }

    @After
    public void tearDown()throws Exception{
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }


    @Test
    public void testCurrentDayButt(){
        Assertions.assertNotNull(lookup("#currentDayMenuItem").query());
        clickOn("#currentDayMenuItem");
    }

    @Test
    public void testAllTaskMenu(){
        Assertions.assertNotNull(lookup("#allTasksMenuItem").query());
        clickOn("#allTasksMenuItem");
        clickOn("#currentDayMenuItem");
    }

    @Test
    public void testAddTask(){
        Assertions.assertNotNull(lookup("#AddTask").query());
        clickOn("#AddTask");
    }

    @Test
    public void testAllTaskMenuAdd(){
        clickOn("#allTasksMenuItem");
        Assertions.assertNotNull(lookup("#AddTask").query());
        clickOn("#AddTask");
    }

    @Test
    public void testtimerMenuItem(){

        Assertions.assertNotNull(lookup("#timerMenuItem").query());
        clickOn("#timerMenuItem");
    }

    @Test
    public void testStartButt(){

        Assertions.assertNotNull(lookup("#timerMenuItem").query());
        clickOn("#timerMenuItem");
        Assertions.assertNotNull(lookup("#startButton").query());
        clickOn("#startButton");
    }

    @Test
    public void testminutesInput(){

        Assertions.assertNotNull(lookup("#timerMenuItem").query());
        clickOn("#timerMenuItem");
        Assertions.assertNotNull(lookup("#minutesInput").query());
        clickOn("#minutesInput");
    }

    @Test
    public void testsecondsInput(){

        Assertions.assertNotNull(lookup("#timerMenuItem").query());
        clickOn("#timerMenuItem");
        Assertions.assertNotNull(lookup("#secondsInput").query());
        clickOn("#secondsInput");
    }

    @Test
    public void testcancelButton(){

        Assertions.assertNotNull(lookup("#timerMenuItem").query());
        clickOn("#timerMenuItem");
        Assertions.assertNotNull(lookup("#startButton").query());
        clickOn("#startButton");
        Assertions.assertNotNull(lookup("#cancelButton").query());
        clickOn("#cancelButton");
    }

    @Test
    public void testAll(){
        Assertions.assertNotNull(lookup("#currentDayMenuItem").query());
        clickOn("#currentDayMenuItem");
        Assertions.assertNotNull(lookup("#AddTask").query());
        clickOn("#AddTask");
        Assertions.assertNotNull(lookup("#allTasksMenuItem").query());
        clickOn("#allTasksMenuItem");
        Assertions.assertNotNull(lookup("#AddTask").query());
        clickOn("#AddTask");
        Assertions.assertNotNull(lookup("#timerMenuItem").query());
        clickOn("#timerMenuItem");
        Assertions.assertNotNull(lookup("#timerMenuItem").query());
        clickOn("#timerMenuItem");

        clickOn("#minutesInput");
        Assertions.assertEquals("0","0");
        clickOn("#secondsInput");
        Assertions.assertEquals("0","0");

        Assertions.assertNotNull(lookup("#startButton").query());
        clickOn("#startButton");
    }

    @Test
    public void testStartButMin(){
        clickOn("#timerMenuItem");
        clickOn("#startButton");
        Assertions.assertEquals("00",lookup("#outputMinutes").queryAs(Label.class).getText());
    }

}