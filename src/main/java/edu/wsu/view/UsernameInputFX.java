package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.controller.UsernameInput;
import edu.wsu.model.Model;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import static edu.wsu.controller.UsernameInput.nameGetter;


//ignore this for sprint 1
public class UsernameInputFX {
    //The model should be called and the name should be passed to the playerCreate
    private static Label nameLabel = new Label("Enter Name");
    //Textfield to take the name
    public static TextField nameField = new TextField();
    //Button to submit the name into the array
    private static Button submitButton = new Button("Submit");

    private static Button exitButton = new Button("Done");

    public static Scene newScene(Model m, App a) {
        VBox root = new VBox(10, nameLabel, nameField, submitButton, exitButton);
        root.setPadding(new Insets(10));

        //Daniil coded this I just copied it
        nameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                if(nameField.getText().equals("")){
                    UsernameInput.complete(m,a);
                }
                else {
                    String input = nameField.getText();
                    nameGetter(input, m, a);
                    nameField.clear();
                }
            }
        });

        submitButton.setOnAction(e ->{
            String input = nameField.getText();
            nameGetter(input, m, a);
            nameField.clear();

        });

        exitButton.setOnAction(e ->{
            UsernameInput.complete(m,a);
        });

        Scene scene = new Scene(root, 400, 350);

        return scene;


    }
}
