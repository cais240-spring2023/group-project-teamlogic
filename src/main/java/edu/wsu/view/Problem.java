package edu.wsu.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;

public class Problem {
    static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    static double screenWidth = screenBounds.getWidth();
    static double screenHeight = screenBounds.getHeight();
    static BackgroundImage wagonBackground = new BackgroundImage(new Image("file:./src/main/resources/blue_wagon.png",screenWidth, screenHeight,false,true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT);
    static Background wagonBackgroundBG = new Background(wagonBackground);

    public static Scene newScene(String string){
        string = "You have " + string + ".";
        if(string.equals("You have died.")) string = string + "\nThis is the afterlife. Did you expect it to be a text box?";
        Label l = new Label(string);
        VBox v = new VBox();
        v.setBackground(wagonBackgroundBG);
        v.getChildren().add(l);
        return new Scene(v,500,600);
    }
}
