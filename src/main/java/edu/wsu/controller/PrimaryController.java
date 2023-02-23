package edu.wsu.controller;

import edu.wsu.App;
import java.io.IOException;

import edu.wsu.model.Player;
import javafx.application.Application;

import javafx.fxml.FXML;



import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//This is where Im going to put the java fx code for now

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimaryController extends Application {


    //Needs a list of names to put into the buttons


    public static List<String> names = new ArrayList<>();

    public static String playerName;

    @Override
    public void start(Stage stage) throws Exception{
        // Create a 2x3 grid pane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));


            //This should make a second window to input the name into the list and have it disappear once the
            // submit button is pressed
            //Current problems is that textwindow appears behind the main window and list doesnt get updated
            // Will try to work on it more tomorrow
            TextField playerField = new TextField();
            Button submitName = new Button("Submit name");
            Stage stageWindow = new Stage();
            VBox root = new VBox();
            root.getChildren().addAll(playerField, submitName);
            Scene nameScene = new Scene(root, 150,150);
            stageWindow.setScene(nameScene);
            stageWindow.show();

            submitName.setOnAction(e -> {
                        playerName = playerField.getText();
                        Player.create(playerName);
                        if (!playerName.isEmpty()) {
                            names.add(playerName);
                        }
                    stageWindow.close();
                    }
            );

            //Loops to fill out the table
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < names.size()) {
                    Button button = new Button(names.get(index));
                    gridPane.add(button, j, i);

                    //disables the button once picked to simulate it being voted out or killed
                    button.setOnAction(event -> {
                        button.setDisable(true);
                    });
                }
            }
        }
        //code for the skip button
        GridPane bottomPane = new GridPane();
        bottomPane.setAlignment(Pos.CENTER);
        Button bottomButton = new Button("Skip Turn");
        bottomButton.setOnAction(event -> {
            System.out.println("Turn has been skipped");
        });

        //Window maker
        Scene scene = new Scene(gridPane, 400, 300);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Player List");
        stage.setScene(scene);
        stage.show();



    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
