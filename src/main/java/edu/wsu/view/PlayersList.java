package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.controller.Server;
import edu.wsu.controller.UsernameInput;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class PlayersList {
    private static Label label = new Label();
    private static String[] names = new String[12];
    private static int index = 0;
    public static Scene newScene(String phase){
        Label phaseLabel = new Label(phase);
        label.setTextAlignment(TextAlignment.CENTER);
        VBox vBox = new VBox();
        vBox.getChildren().add(phaseLabel);
        vBox.getChildren().add(label);
        Button b = new Button("Launch");
        b.setOnAction(event -> {
            Server.launch(names);
        });
        vBox.getChildren().add(b);
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox,600,600);
    }
    public static void addName(String name){
        if(index < 12) {
            System.out.println("Added " + name);
            names[index] = name;
            index++;
            if(index == 12) label.setText(label.getText() + "\n" + name + "\nALL SLOTS FILLED... PRESS LAUNCH TO BEGIN GAME");
            else label.setText(label.getText() + "\n" + name);
        }
    }
}
