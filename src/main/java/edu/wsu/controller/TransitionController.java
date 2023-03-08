package edu.wsu.controller;

import edu.wsu.model.Model;
import edu.wsu.view.TransitionView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
    Model model;
    public TransitionController(){
    }

    public void startTurn(ActionEvent event){
        model.clearTransitionView();
        //TODO: re-open the main window
    }
}
