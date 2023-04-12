package edu.wsu.view;

import edu.wsu.App;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PlayersList {
    private static Label label = new Label();
    public static Scene newScene(){
        VBox vBox = new VBox();
        vBox.getChildren().add(label);
        Button b = new Button("Launch");
        vBox.getChildren().add(b);
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox,600,600);
    }
    public static void addName(String name){
        label.setText(label.getText() + "\n" + name);
    }
}
