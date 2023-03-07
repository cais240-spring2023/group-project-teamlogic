package edu.wsu.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MessageDisplayerFX extends Application {

    public void start(Stage primaryStage){
        StackPane root = new StackPane();
        Button close = new Button();
        close.setOnAction(event -> {primaryStage.close();});
        root.getChildren().add(close);
        Scene scene = new Scene(root,300,250);
        primaryStage.setTitle("Title");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
