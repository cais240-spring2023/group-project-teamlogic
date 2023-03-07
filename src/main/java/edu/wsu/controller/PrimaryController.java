package edu.wsu.controller;
import edu.wsu.App;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//This is where Im going to put the java fx code for now

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class PrimaryController extends Application {

    //Creates an array of names with the size of 12
    public static String[] playerName = new String[12];
    //Index of the array used to assign names to slots
    private int currentIndex = 0;
    //Label to instruct the user to input the name
    private Label nameLabel = new Label("Enter Name");
    //Textfield to take the name
    public TextField nameField = new TextField();
    //Button to submit the name into the array
    private Button submitButton = new Button("Submit");
    @Override
    public void start(Stage stage) throws Exception{
        // Create a 2x3 grid pane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));


            String input = nameField.getText();
            playerName[currentIndex] = input; // add the name to the array
            currentIndex++; // increment the index
            nameField.clear(); // clear the text field

            submitName.setOnAction(e -> {
                for (int i = 0; i < size; i++) {
                    playerName = String.valueOf(playerField);
                     names[i] = playerName;
                        }
                    stageWindow.close();
                    }
            );

            //Loops to fill out the table
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < size) {
                    Button button = new Button(names[i]);
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
