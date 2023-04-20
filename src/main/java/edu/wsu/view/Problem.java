package edu.wsu.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Problem {
    public static Scene newScene(String string){
        string = "You have " + string + ".";
        if(string.equals("You have died.")) string = string + "\nThis is the afterlife. Did you expect it to be a text box?";
        Label l = new Label(string);
        VBox v = new VBox();
        v.getChildren().add(l);
        return new Scene(v,200,600);
    }
}
