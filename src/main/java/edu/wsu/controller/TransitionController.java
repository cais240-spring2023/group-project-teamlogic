package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Model;
import edu.wsu.model.Player;
import edu.wsu.view.TransitionView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TransitionController
{
    /*@FXML
    public static Button takeTurn;
    @FXML
    public static Label currentPlayerMessage;
    @FXML
    public static Label playerName;
    @FXML
    public ImageView playerImage;
    @FXML
    public Button exitButton;
*/
    public TransitionController(){
    }

    public static Scene newScene(String name, App app){
        StackPane root = new StackPane();
        //Label playerName = new Label();
        //playerName.setText(name);
        Label currentPlayerMessage = new Label();
        currentPlayerMessage.setText("It is your turn: " + name + " Click below to begin.");
        Button takeTurn = new Button();
        takeTurn.setText("Take turn");
        takeTurn.setOnAction(event -> {app.doNext();});
        StackPane.setAlignment(takeTurn, Pos.BOTTOM_CENTER);
        //root.getChildren().add(playerName);
        root.getChildren().add(currentPlayerMessage);
        root.getChildren().add(takeTurn);
        return new Scene(root,300,250);
    }

    public static void display(String name, App app){
        app.changeScene(newScene(name, app));
    }
}
