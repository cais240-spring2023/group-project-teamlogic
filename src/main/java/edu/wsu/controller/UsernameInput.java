package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Model;
<<<<<<< Updated upstream
import edu.wsu.model.Player;
import edu.wsu.model.Players;
import edu.wsu.view.UsernameInputFX;
import javafx.application.Application;
=======
>>>>>>> Stashed changes

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;


//ignore this for sprint 1
public class UsernameInput {
    //The model should be called and the name should be passed to the playerCreate


    //Creates an array of names with the size of 12
    public static String[] playerName = new String[6];

    //Index of the array used to assign names to slots
    public static int currentIndex = 0;
    //Label to instruct the user to input the name
<<<<<<< Updated upstream
=======
    private static Label nameLabel = new Label("Enter Name");
    //Textfield to take the name
    public static TextField nameField = new TextField();
    //Button to submit the name into the array
    private static Button submitButton = new Button("Submit");

    private static Button exitButton = new Button("Done");

    public static Scene newScene(Model m, App a) {
        VBox root = new VBox(10, nameLabel, nameField, submitButton, exitButton);

        root.setPadding(new Insets(10));

        nameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (nameField.getText().equals("")) {
                    m.addPlayersPhase(playerName);
                    a.beginGame(m);
                }
                String input = nameField.getText();
                nameGetter(input, m, a);

            }
        });

        submitButton.setOnAction(e -> {
            String input = nameField.getText();
            nameGetter(input, m, a);

        });

        exitButton.setOnAction(e -> {

            m.addPlayersPhase(playerName);
            a.beginGame(m);
        });

        Scene scene = new Scene(root, 400, 350);

        return scene;


    }
>>>>>>> Stashed changes

    //Move stage over to App
    //return the scene to app
    //end of startGame
    //At the end change the button from closing to beginGame
    public static void nameGetter(String input, Model m, App a) {
        playerName[currentIndex] = input;
        currentIndex++; // increment the index
        System.out.println("Name added: " + input); //Just making sure the name got processed
<<<<<<< Updated upstream
        if(currentIndex >= 12){
=======
        if (currentIndex > 12) {
>>>>>>> Stashed changes
            System.out.println("No more room");
            complete(m,a);
        }

    }

<<<<<<< Updated upstream
    public static void complete(Model m, App a){
        m.addPlayersPhase(playerName);
        a.beginGame(m);
    }

    public static void namer(Model m, App a) {
        a.changeScene(UsernameInputFX.newScene(m,a));
=======
    public static void namer(Model m, App a) {
        a.changeScene(newScene(m, a));
>>>>>>> Stashed changes
    }

}
