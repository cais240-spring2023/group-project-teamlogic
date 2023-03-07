package edu.wsu.controller;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MessageDisplayerFX {

    public static Stage newStage(String name, String messages){
        Stage newStage = new Stage();
        StackPane root = new StackPane();
        Button close = new Button();
        close.setText("Okay");
        close.setOnAction(event -> {newStage.close();});
        StackPane.setAlignment(close, Pos.BOTTOM_CENTER);
        Label text = new Label();
        text.setText(messages);
        root.getChildren().add(text);
        root.getChildren().add(close);
        Scene scene = new Scene(root,300,250);
        newStage.setTitle(name);
        newStage.setScene(scene);
        return newStage;
    }

}
