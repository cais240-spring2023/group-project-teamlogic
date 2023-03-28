package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Model;
import edu.wsu.model.ModelSingleton;
import edu.wsu.model.Player;
import edu.wsu.model.Players;
import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//ignore this for sprint 1
public class UsernameInput{
    //The model should be called and the name should be passed to the playerCreate


    //Creates an array of names with the size of 12
    public static String[] playerName = new String[12];
    //Index of the array used to assign names to slots
    public static int currentIndex = 0;
    //Label to instruct the user to input the name
    private static Label nameLabel = new Label("Enter Name");
    //Textfield to take the name
    public static TextField nameField = new TextField();
    //Button to submit the name into the array
    private static Button submitButton = new Button("Submit");
    private static Button exitButton = new Button("Done");
    Model model;

    public UsernameInput(){
        model = ModelSingleton.getInstance();
    }

    public static Scene newScene(Model m, App a) {
        VBox root = new VBox(10, nameLabel, nameField, submitButton, exitButton);
        root.setPadding(new Insets(10));

        //Daniil coded this I just copied it
        nameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                if(nameField.getText().equals("")){
                    m.addPlayersPhase(playerName);
                    a.beginGame();
                }
                else {
                    String input = nameField.getText();
                    nameGetter(input, m, a);
                }
            }
        });

        submitButton.setOnAction(e ->{
            String input = nameField.getText();
            nameGetter(input, m, a);

        });

        exitButton.setOnAction(e ->{

            m.addPlayersPhase(playerName);
            a.beginGame();
        });

        Scene scene = new Scene(root, 400, 350);

        return scene;


    }

    //Move stage over to App
    //return the scene to app
    //end of startGame
    //At the end change the button from closing to beginGame
    public static void nameGetter(String input, Model m, App a){
        playerName[currentIndex] = input;
        currentIndex++; // increment the index
        nameField.clear(); // clear the text field
        System.out.println("Name added: " + input); //Just making sure the name got processed
        if(currentIndex >= 12){
            System.out.println("No more room");
            m.addPlayersPhase(playerName);
            a.beginGame();
        }

    }
    public static void namer(Model m, App a) {
        a.changeScene(newScene(m,a));
    }
}
