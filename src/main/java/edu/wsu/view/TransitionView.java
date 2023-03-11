package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.controller.TransitionController;
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
    /*@FXML
    public Button takeTurn;
    @FXML
    public Label currentPlayerMessage;
    @FXML
    public Label playerName;
    @FXML
    public ImageView playerImage;
    @FXML
    public Button exitButton;

    Model model = new Model();
    Stage stage;
    App app;
    TransitionController controller;

    public TransitionView(){
    }

    private void initialize(){
        playerName.setText(model.whoseTurnIsIt().getName());
        currentPlayerMessage.setText("It is your turn: " + model.whoseTurnIsIt().getName()
                + " Click below to begin.");
        takeTurn.setOnAction(this::startTurn);
        //TODO: set the image
    }
    @FXML
    public void startTurn(ActionEvent event, App app){
        controller.startTurn(event, app);
    }

    public void setController(TransitionController controller){
        this.controller = controller;
    }

    public void closeStage(){
        stage.close();
    }

*/
}
