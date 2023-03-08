package edu.wsu.controller;

import edu.wsu.App;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class MessageDisplayerFX {

    static boolean done = false;

    public static Scene newScene(String name, String messages){
        done = false;
        StackPane root = new StackPane();
        Button close = new Button();
        close.setText("Okay");
        close.setOnAction(event -> {done = true;});
        StackPane.setAlignment(close, Pos.BOTTOM_CENTER);
        Label text = new Label();
        text.setText(name + "...\n" + messages);
        root.getChildren().add(text);
        root.getChildren().add(close);
        return new Scene(root,300,250);
    }

    public static void display(String name, String messages, App app){
        app.changeScene(newScene(name, messages));
    }
    public static boolean waiting(){
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        return true;
    }

}
