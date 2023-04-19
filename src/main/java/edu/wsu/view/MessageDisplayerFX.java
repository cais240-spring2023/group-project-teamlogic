package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.controller.Client;
import edu.wsu.model.Model;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.util.Scanner;

public class MessageDisplayerFX {

    public static Scene newScene(String name, String messages, App app, Model m){
        StackPane root = new StackPane();
        Button close = new Button();
        close.setText("Okay");
        close.setOnAction(event -> {
            if(App.inHotseat) app.next();
            else{
                if(name.charAt(name.length()-1) == '.'){//This will be true during good morning
                    if(m.getTurn() == 0){
                        Client.nightPhase();
                    }
                    else{
                        Client.voting();
                    }
                }
                else if(name.charAt(name.length()-1) == ' '){
                    Client.nightPhase();
                }
                else{
                    Platform.exit();
                }
            }
        });
        StackPane.setAlignment(close, Pos.BOTTOM_CENTER);
        Label text = new Label();
        text.setText(name + "...\n" + messages);
        root.getChildren().add(text);
        root.getChildren().add(close);
        return new Scene(root,600,600);
    }

    public static void display(String name, String messages, App app, Model m){
        app.changeScene(newScene(name, messages, app, m));
    }
    public static boolean waiting(){
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        return true;
    }

}
