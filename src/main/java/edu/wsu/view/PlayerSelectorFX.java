package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.controller.PlayerSelector;
import edu.wsu.model.Model;
import edu.wsu.model.ModelSingleton;
import edu.wsu.model.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class PlayerSelectorFX {

    public static Scene newScene(Player[] players, Player chooser, String purpose, App app, Model m){
        final int BUTTON_WIDTH = 75;
        final Player[] selection = {null};
        m = ModelSingleton.getInstance();
        GridPane grid = new GridPane();

        TextField textField = new TextField();

        Button p1 = new Button();
        TextField finalTextField = textField;
        p1.setOnAction(event -> {
            if(players.length >= 1) selection[0] = players[0];
            if(finalTextField.getText().equals("")) app.receive(chooser,selection[0],purpose);
            else PlayerSelector.sendMessage(selection[0],chooser,finalTextField.getText(),app);
        });

        Button p2 = new Button();
        p2.setOnAction(event -> {
            if(players.length >= 2) selection[0] = players[1];
            if(finalTextField.getText().equals("")) app.receive(chooser,selection[0],purpose);
            else PlayerSelector.sendMessage(selection[0],chooser,finalTextField.getText(),app);
        });

        Button p3 = new Button();
        p3.setOnAction(event -> {
            if(players.length >= 3) selection[0] = players[2];
            if(finalTextField.getText().equals("")) app.receive(chooser,selection[0],purpose);
            else PlayerSelector.sendMessage(selection[0],chooser,finalTextField.getText(),app);
        });

        Button p4 = new Button();
        p4.setOnAction(event -> {
            if(players.length >= 4) selection[0] = players[3];
            if(finalTextField.getText().equals("")) app.receive(chooser,selection[0],purpose);
            else PlayerSelector.sendMessage(selection[0],chooser,finalTextField.getText(),app);
        });

        Button p5 = new Button();
        p5.setOnAction(event -> {
            if(players.length >= 5) selection[0] = players[4];
            if(finalTextField.getText().equals("")) app.receive(chooser,selection[0],purpose);
            else PlayerSelector.sendMessage(selection[0],chooser,finalTextField.getText(),app);
        });

        Button p6 = new Button();
        p6.setOnAction(event -> {
                    if (players.length >= 6) selection[0] = players[5];
                    if (finalTextField.getText().equals("")) app.receive(chooser, selection[0], purpose);
                    else PlayerSelector.sendMessage(selection[0],chooser,finalTextField.getText(),app);
                });

        Button p7 = new Button();
        p7.setOnAction(event -> {
            if(players.length >= 7) selection[0] = players[6];
            if(finalTextField.getText().equals("")) app.receive(chooser,selection[0],purpose);
            else PlayerSelector.sendMessage(selection[0],chooser,finalTextField.getText(),app);
        });

        Button p8 = new Button();
        p8.setOnAction(event -> {
            if(players.length >= 8) selection[0] = players[7];
            if(finalTextField.getText().equals("")) app.receive(chooser,selection[0],purpose);
            else PlayerSelector.sendMessage(selection[0],chooser,finalTextField.getText(),app);
        });

        Button p9 = new Button();
        p9.setOnAction(event -> {
            if(players.length >= 9) selection[0] = players[8];
            if(finalTextField.getText().equals("")) app.receive(chooser,selection[0],purpose);
            else PlayerSelector.sendMessage(selection[0],chooser,finalTextField.getText(),app);
        });

        Button p10 = new Button();
        p10.setOnAction(event -> {
            if(players.length >= 10) selection[0] = players[9];
            if(finalTextField.getText().equals("")) app.receive(chooser,selection[0],purpose);
            else PlayerSelector.sendMessage(selection[0],chooser,finalTextField.getText(),app);
        });

        Button p11 = new Button();
        p11.setOnAction(event -> {
            if(players.length >= 11) selection[0] = players[10];
            if(finalTextField.getText().equals("")) app.receive(chooser,selection[0],purpose);
            else PlayerSelector.sendMessage(selection[0],chooser,finalTextField.getText(),app);
        });

        Button p12 = new Button();
        p12.setOnAction(event -> {
            if(players.length >= 12) selection[0] = players[11];
            if(finalTextField.getText().equals("")) app.receive(chooser,selection[0],purpose);
            else PlayerSelector.sendMessage(selection[0],chooser,finalTextField.getText(),app);
        });

        Button[] buttons = {p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12};
        for(int i = 0; i < buttons.length; i++){
            buttons[i].setPrefWidth(BUTTON_WIDTH);
        }

        textField.setPrefWidth(2*BUTTON_WIDTH);

        Button skip = new Button();
        skip.setText("Skip");
        skip.setPrefWidth(2*BUTTON_WIDTH);
        skip.setOnAction(event -> {app.next();});

        Label text = new Label();
        if(purpose.equals("vote against")) text.setText(chooser.getName() + ", select a player to " + purpose + ".");
        else text.setText(chooser.getName() + ", select a player to " + purpose + "send a message to.");

        for(int c = 0; c < 2; c++){
            for(int r = 0; r < 6; r++){
                int i = c*6+r;
                if(i >= players.length) break;
                if(players[i] != null && players[i].isAlive()){
                    grid.add(buttons[i],c,r);
                    buttons[i].setText(players[i].getName());
                }
            }
        }
        VBox root = new VBox();
        root.getChildren().add(text);
        grid.setAlignment(Pos.CENTER);
        root.getChildren().add(grid);
        if(!purpose.equals("vote against")) root.getChildren().add(textField);
        root.getChildren().add(skip);
        root.setAlignment(Pos.CENTER);
        return new Scene(root,600,500);
    }
}
