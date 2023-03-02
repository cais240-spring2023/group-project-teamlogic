package edu.wsu.controller;

import edu.wsu.App;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

//ignore this for sprint 1
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
    public void start(Stage mainStage) throws Exception {
        VBox root = new VBox(10, nameLabel, nameField, submitButton);
        root.setPadding(new Insets(10));

        submitButton.setOnAction(e ->{

            String input = nameField.getText();
            playerName[currentIndex] = input; // add the name to the array
            currentIndex++; // increment the index
            nameField.clear(); // clear the text field

            System.out.println("Name added: " + input); //Just making sure the name got processed
        });

        Scene scene = new Scene(root, 300, 200);

        mainStage.setScene(scene);
        mainStage.setTitle("Names");
        mainStage.show();

    }

    public void displayWindow() {
        Label listLabel = new Label("List: ");

        StringBuilder namesText = new StringBuilder();
        for(String input : playerName){
            namesText.append(input).append("\n");
        }
        TextArea nameArea = new TextArea(namesText.toString());
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> System.exit(0));


        VBox root = new VBox(10, listLabel, nameArea, closeButton);
        root.setPadding(new Insets(10));

        Stage namesStage = new Stage();
        Scene scene = new Scene(root, 300, 200);
        namesStage.setScene(scene);
        namesStage.setTitle("Names List");
        namesStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }


    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }


}
