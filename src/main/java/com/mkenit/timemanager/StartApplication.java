package com.mkenit.timemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("templates/CurrentDay.fxml"));
        Parent root=fxmlLoader.load();
        Scene scene = new Scene(root, 960, 540);
        CurrentDayScene mainPageController=new CurrentDayScene();
        stage.setTitle("Текущие задачи");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnHidden(e->mainPageController.appShutdown());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

class StartAppWithoutModule {
    public static void main(String[] args) {
        StartApplication.main(args);
    }
}