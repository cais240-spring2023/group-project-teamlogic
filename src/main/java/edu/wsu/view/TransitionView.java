package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.controller.TransitionController;
import edu.wsu.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TransitionView {


    public static Scene newScene(String name, String purpose, App app){
        StackPane root = new StackPane();
        //Label playerName = new Label();
        //playerName.setText(name);
        Label currentPlayerMessage = new Label();
        currentPlayerMessage.setText("It is your turn to " + purpose + ", " + name + ".\nClick below to begin.");
        Button takeTurn = new Button();
        takeTurn.setText("Take turn");
        takeTurn.setOnAction(event -> {app.doNext();});
        StackPane.setAlignment(takeTurn, Pos.BOTTOM_CENTER);
        //root.getChildren().add(playerName);
        root.getChildren().add(currentPlayerMessage);
        root.getChildren().add(takeTurn);
        return new Scene(root,600,500);
    }
}
