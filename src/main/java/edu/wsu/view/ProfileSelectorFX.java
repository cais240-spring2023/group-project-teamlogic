package edu.wsu.view;

import edu.wsu.controller.Client;
import edu.wsu.controller.UsernameInput;
import edu.wsu.model.Model;
import edu.wsu.model.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import edu.wsu.App;


public class ProfileSelectorFX {
    static String[] names = {"Bertie","Brenden","Dan","Domino","Evan","Kenneth","Logan","Miner","Nick","Ruth","Spencer","Tim"};

    public static Scene newScene(Model m, App a) {
        ComboBox<String> comboBox = new ComboBox<>();
        for(int i = 0; i < names.length; i++) comboBox.getItems().add(names[i]);
        Image image = App.getNullImage();
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(280);
        imageView.setFitWidth(210);
        Button button  = new Button("Submit");
        comboBox.setPrefWidth(210);
        button.setPrefWidth(210);
        VBox root = new VBox();
        root.getChildren().add(comboBox);
        root.getChildren().add(imageView);
        root.getChildren().add(button);
        if(App.inHotseat){
            Button finish = new Button("Finished");
            finish.setPrefWidth(210);
            root.getChildren().add(finish);
            finish.setOnAction(event -> UsernameInput.complete(a));
        }
        root.setAlignment(Pos.CENTER);
        comboBox.setOnAction(event -> {
            if(comboBox.getValue() != null) imageView.setImage(App.getImage(comboBox.getValue()));
        });
        button.setOnAction(event ->{

            if(App.inHotseat) {
                UsernameInput.nameGetter(comboBox.getValue(), m, a);
            }
            else{
                Client.sendMessage(comboBox.getValue());
                Client.beginGame();
            }
            comboBox.getSelectionModel().clearSelection();
            imageView.setImage(new Image("file:./src/main/resources/estor.png"));
                });
        return new Scene(root,App.V0,App.V1);
    }
}
