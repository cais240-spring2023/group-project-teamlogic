package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.controller.PlayerSelector;
import edu.wsu.model.Model;
import edu.wsu.model.ModelSingleton;
import edu.wsu.model.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class PlayerSelectorPics {

    public static Scene newScene(Player[] players, Player chooser, String purpose, App app, Model m){
        final int BUTTON_WIDTH = 75;
        final Player[] selection = {null};
        m = ModelSingleton.getInstance();
        GridPane grid = new GridPane();
        VBox vbox;
        ImageView imageView;
        Button b;
        TextField textField = new TextField();
        textField.setPrefWidth(420);


        for(int r = 0; r < 4; r++){
            for(int c = 0; c < 3; c++){
                int i = c*4+r;
                if(players[i] != null && players[i].isAlive()){
                    vbox = new VBox();
                    final int index = i;
                    imageView = new ImageView(new Image("file:./src/main/resources/" + players[index].getName().toLowerCase() + ".png"));
                    imageView.setFitHeight(140);
                    imageView.setFitWidth(105);
                    b = new Button(players[index].getName());
                    b.setPrefWidth(105);
                    b.setOnAction(event -> {
                                selection[0] = players[index];
                                if(textField.getText().equals("")) app.receive(chooser,selection[0],purpose);
                                else PlayerSelector.sendMessage(selection[0],chooser,textField.getText(),app);
                            }
                    );
                    vbox.getChildren().add(imageView);
                    vbox.getChildren().add(b);
                    grid.add(vbox,r,c);
                }
            }
        }
        vbox = new VBox();
        if(purpose.equals("vote against")) vbox.getChildren().add(new Label(chooser.getName() + ", select a player to " + purpose + "."));
        else vbox.getChildren().add(new Label(chooser.getName() + ", select a player to " + purpose + "send a message to."));
        grid.setAlignment(Pos.CENTER);
        vbox.getChildren().add(grid);
        if(!purpose.equals("vote against")) vbox.getChildren().add(textField);
        b = new Button("Skip");
        b.setPrefWidth(420);
        b.setOnAction(event -> app.next());
        vbox.getChildren().add(b);
        vbox.setAlignment(Pos.CENTER);
        return new Scene(vbox,500,600);
    }
}
