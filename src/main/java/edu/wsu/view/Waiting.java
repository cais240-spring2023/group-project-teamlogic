package edu.wsu.view;

import edu.wsu.App;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;

public class Waiting {
    static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    static double screenWidth = screenBounds.getWidth();
    static double screenHeight = screenBounds.getHeight();
    static BackgroundImage wagonBackground = new BackgroundImage(new Image("file:./src/main/resources/blue_wagon.png",screenWidth, screenHeight,false,true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT);
    static Background wagonBackgroundBG = new Background(wagonBackground);
    public static Scene newScene(){
        Label l = new Label("Sit tight!");
        VBox vbox = new VBox();
        vbox.setBackground(wagonBackgroundBG);
        vbox.getChildren().add(l);

        return new Scene(vbox, App.V0,App.V1);
    }
}
