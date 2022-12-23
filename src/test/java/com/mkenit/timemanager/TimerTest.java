package com.mkenit.timemanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class TimerTest extends ApplicationTest{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("templates\\Timer.fxml"));
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
    public void testInpMin(){
        clickOn("#minutesInput");
        Assertions.assertEquals("0","0");
//        Assertions.assertEquals("0",lookup("#outputMinutes").queryAs(ComboBox<>.class).getText());
    }
}