package edu.wsu.view;

import edu.wsu.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TransitionView {
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

    private static Scene scene;
    AnchorPane transitionPane;

    public TransitionView(){}

    private void initialize(){
        currentPlayerMessage.setText("It is your turn: " + /*playerName*/ " Click below to begin.");
    }
}
