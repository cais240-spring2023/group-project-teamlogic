package edu.wsu.view;

import edu.wsu.App;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Waiting {
    public static Scene newScene(){
        Label l = new Label("Sit tight!");
        VBox vbox = new VBox();
        vbox.getChildren().add(l);
        return new Scene(vbox, App.V0,App.V1);
    }
}
