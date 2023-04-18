package edu.wsu.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Waiting {
    public static Scene newScene(){
        Label l = new Label("Sit tight!");
        VBox vbox = new VBox();
        vbox.getChildren().add(l);
        return new Scene(l,500,600);
    }
}
