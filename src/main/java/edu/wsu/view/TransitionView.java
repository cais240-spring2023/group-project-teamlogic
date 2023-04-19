package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.controller.TransitionController;
import edu.wsu.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;


import static edu.wsu.view.PlayerSelectorFX.cityBackgroundBG;

public class TransitionView {
    static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    static double screenWidth = screenBounds.getWidth();
    static double screenHeight = screenBounds.getHeight();

    static BackgroundImage cityBackground = new BackgroundImage(new Image("file:./src/main/resources/city background.png",screenWidth, screenHeight,false,true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT);


    static Background nightBackgroundBG = new Background(cityBackground);

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
        root.setBackground(nightBackgroundBG);
        root.getChildren().add(currentPlayerMessage);
        root.getChildren().add(takeTurn);
        return new Scene(root,600,600);
    }
}
