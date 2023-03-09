package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PlayerSelectorFX {

    public static Scene newScene(Player[] players, Player chooser, String purpose, App app){
        final int BUTTON_WIDTH = 75;
        final Player[] selection = {null};
        GridPane grid = new GridPane();

        Button p1 = new Button();
        p1.setOnAction(event -> {
            if(players.length >= 1) selection[0] = players[0];
            app.receive(chooser,selection[0],purpose);
        });

        Button p2 = new Button();
        p2.setOnAction(event -> {
            if(players.length >= 2) selection[0] = players[1];
            app.receive(chooser,selection[0],purpose);
        });

        Button p3 = new Button();
        p3.setOnAction(event -> {
            if(players.length >= 3) selection[0] = players[2];
            app.receive(chooser,selection[0],purpose);
        });

        Button p4 = new Button();
        p4.setOnAction(event -> {
            if(players.length >= 4) selection[0] = players[3];
            app.receive(chooser,selection[0],purpose);
        });

        Button p5 = new Button();
        p5.setOnAction(event -> {
            if(players.length >= 5) selection[0] = players[4];
            app.receive(chooser,selection[0],purpose);
        });

        Button p6 = new Button();
        p6.setOnAction(event -> {
            if(players.length >= 6) selection[0] = players[5];
            app.receive(chooser,selection[0],purpose);
        });

        Button p7 = new Button();
        p7.setOnAction(event -> {
            if(players.length >= 7) selection[0] = players[6];
            app.receive(chooser,selection[0],purpose);
        });

        Button p8 = new Button();
        p8.setOnAction(event -> {
            if(players.length >= 8) selection[0] = players[7];
            app.receive(chooser,selection[0],purpose);
        });

        Button p9 = new Button();
        p9.setOnAction(event -> {
            if(players.length >= 9) selection[0] = players[8];
            app.receive(chooser,selection[0],purpose);
        });

        Button p10 = new Button();
        p10.setOnAction(event -> {
            if(players.length >= 10) selection[0] = players[9];
            app.receive(chooser,selection[0],purpose);
        });

        Button p11 = new Button();
        p11.setOnAction(event -> {
            if(players.length >= 11) selection[0] = players[10];
            app.receive(chooser,selection[0],purpose);
        });

        Button p12 = new Button();
        p12.setOnAction(event -> {
            if(players.length >= 12) selection[0] = players[11];
            app.receive(chooser,selection[0],purpose);
        });

        Button[] buttons = {p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12};
        for(int i = 0; i < buttons.length; i++){
            buttons[i].setPrefWidth(BUTTON_WIDTH);
        }

        Button skip = new Button();
        skip.setText("Skip");
        skip.setOnAction(event -> {app.next();});

        Label text = new Label();
        text.setText(chooser.getName() + ", select a player to " + purpose);

        for(int c = 0; c < 2; c++){
            for(int r = 0; r < 6; r++){
                int i = c*6+r;
                if(i >= players.length) break;
                if(players[i].isAlive()){
                    grid.add(buttons[i],c,r);
                    buttons[i].setText(players[i].getName());
                }
            }
        }
        VBox root = new VBox();
        root.getChildren().add(text);
        root.getChildren().add(grid);
        root.getChildren().add(skip);
        return new Scene(root,300,250);
    }

    public static void choose(Player[] players, Player chooser, String purpose, App app){
        app.changeScene(newScene(players,chooser,purpose,app));
    }
}
