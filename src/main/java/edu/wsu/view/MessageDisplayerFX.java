package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.model.Model;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;

import java.util.Scanner;

public class MessageDisplayerFX {
    static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    static double screenWidth = screenBounds.getWidth();
    static double screenHeight = screenBounds.getHeight();

    static BackgroundImage cityBackground = new BackgroundImage(new Image("file:./src/main/resources/city background.png",screenWidth, screenHeight,false,true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT);


    static Background morningBackgroundBG = new Background(cityBackground);

    public static Scene newScene(String name, String messages, App app, Model m){
        StackPane root = new StackPane();
        root.setBackground(morningBackgroundBG);
        Button close = new Button();
        close.setText("Okay");
        close.setOnAction(event -> {app.next();});
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
