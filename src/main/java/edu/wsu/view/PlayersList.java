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
    private static Label label;
    private static String[] names;
    private static int index = 0;
    public static Scene newScene(String phase){
        System.out.println("Players list");
        index = 0;
        label = new Label();
        names = new String[12];
        Label phaseLabel = new Label(phase);
        label.setTextAlignment(TextAlignment.CENTER);
        VBox vBox = new VBox();
        vBox.getChildren().add(phaseLabel);
        vBox.getChildren().add(label);
        if(phase.equals("Joined players...")) {
            Button b = new Button("Launch");
            b.setOnAction(event -> {
                if(index >= 2) Server.launch();
            });
            vBox.getChildren().add(b);
        }
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox,App.V0,App.V1);
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
    public static boolean has(String name){
        for(int i = 0; i < names.length; i++){
            if(name.equals(names[i])) return true;
        }
        return false;
    }
}
