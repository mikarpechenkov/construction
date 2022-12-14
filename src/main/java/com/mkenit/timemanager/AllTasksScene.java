package com.mkenit.timemanager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class AllTasksScene {
    @FXML
    private Button AddTask;

    @FXML
    private TableColumn<?, ?> durationTask;

    @FXML
    private TableColumn<?, ?> importanceColumn;

    @FXML
    private TableColumn<?, ?> statusColumn;

    @FXML
    private TableView<?> tableOfTasks;

    @FXML
    private TableColumn<?, ?> taskColumn;

    @FXML
    private TableColumn<?, ?> timeColumn;


}
