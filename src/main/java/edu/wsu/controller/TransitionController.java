package edu.wsu.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TransitionController
{
    @FXML
    public Button takeTurn;
    @FXML
    public Label currentPlayerMessage;
    @FXML
    public Label playerName;
    @FXML
    public ImageView playerImage;
    @FXML
    public Button exitButton;

    public void handleButtonAction(ActionEvent actionEvent){
        Node transition = (Node)actionEvent.getSource();
        Stage stage = (Stage) transition.getScene().getWindow();

    }
}
