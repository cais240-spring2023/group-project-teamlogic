package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.controller.Client;
import edu.wsu.controller.UsernameInput;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import static edu.wsu.controller.UsernameInput.nameGetter;

public class Connector {
    private static Label label = new Label("Input host name and port number (default port number: 4544)");
    private static TextField textField = new TextField();
    private static Button button = new Button("Submit");
    public static Scene newScene(App a){
        VBox vbox = new VBox();
        vbox.getChildren().add(label);
        vbox.getChildren().add(textField);
        vbox.getChildren().add(button);
        button.setOnAction(event -> connect(a));
        //Daniil coded this I just copied it
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                connect(a);
            }
        });
        return new Scene(vbox,300,300);
    }
    public static void connect(App a){
        String hostName;
        int port;
        if(textField.getText().contains(":")){
            hostName = textField.getText().split(":")[0];
            port = Integer.parseInt(textField.getText().split(":")[1]);
        }
        else{
            hostName = textField.getText();
            port = 4544;
        }
        Client.launchClient(hostName,port,a);
    }
}
