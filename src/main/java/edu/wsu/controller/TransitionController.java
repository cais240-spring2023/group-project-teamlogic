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

    public static void display(String name, String purpose, App app, Model m){
        app.changeScene(TransitionView.newScene(name, purpose, app, m));
    }
}
