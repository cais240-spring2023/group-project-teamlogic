package edu.wsu.view;

import edu.wsu.App;
import edu.wsu.model.Model;
import edu.wsu.model.Player;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.lang.reflect.Array;

public class PlayerSelectorFX {

    public static Scene newScene(Player[] players, Player chooser, String purpose, App app, Model m){
        final int BUTTON_WIDTH = 75;
        final Player[] selection = {null};
        GridPane grid = new GridPane();

        Button p1 = makeButton(players, 1, selection, 0, app, chooser, purpose, m);

        Button p2 = makeButton(players, 2, selection, 1, app, chooser, purpose, m);

        Button p3 = makeButton(players, 3, selection, 2, app, chooser, purpose, m);

        Button p4 = makeButton(players, 4, selection, 3, app, chooser, purpose, m);

        Button p5 = makeButton(players, 5, selection, 4, app, chooser, purpose, m);

        Button p6 = makeButton(players, 6, selection, 5, app, chooser, purpose, m);

        Button p7 = makeButton(players, 7, selection, 6, app, chooser, purpose, m);

        Button p8 = makeButton(players, 8, selection, 7, app, chooser, purpose, m);

        Button p9 = makeButton(players, 9, selection, 8, app, chooser, purpose, m);

        Button p10 = makeButton(players, 10, selection, 9, app, chooser, purpose, m);

        Button p11 = makeButton(players, 11, selection, 10, app, chooser, purpose, m);

        Button p12 = makeButton(players, 12, selection, 11, app, chooser, purpose, m);

        Button[] buttons = {p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12};
        for(int i = 0; i < buttons.length; i++){
            if (buttons[i] != null) {
                buttons[i].setPrefWidth(BUTTON_WIDTH);
            }
        }

        Button skip = new Button();
        skip.setText("Skip");
        skip.setPrefWidth(2*BUTTON_WIDTH);
        skip.setOnAction(event -> {app.next(m);});

        Label text = new Label();
        text.setText(chooser.getName() + ", select a player to " + purpose);

        FlowPane train = new FlowPane(Orientation.HORIZONTAL);
        train.setVgap(8);
        train.setHgap(4);
        train.setPrefWrapLength(30);
        FlowPane[] cars = new FlowPane[4];
        for (int i = 0; i < 4; i++) {
            FlowPane car = new FlowPane(Orientation.VERTICAL);
            car.setVgap(8);
            car.setHgap(4);
            car.setPadding(new Insets(10));
            car.setBackground(Background.fill(Color.OLIVE));
            car.setPrefWrapLength(30);
            train.getChildren().add(car);
            cars[i] = car;
        }
        for (int x = 0; x < players.length; x++) {
            if (players[x] != null && players[x].isAlive()) {
                int currentCar = x % 4;
                cars[currentCar].getChildren().add(buttons[x]);
            }
        }
        //        for(int col = 0; col < 2; col++){
//            for(int row = 0; row < 6; row++){
//                int i = col*6+row;
//                if(i >= players.length) break;
//                if(players[i] != null && players[i].isAlive()){
//                    grid.add(buttons[i],col,row);
//                    buttons[i].setText(players[i].getName());
//                }
//            }
//        }
        VBox root = new VBox();
        root.getChildren().add(text);
        //root.getChildren().add(grid);
        root.getChildren().add(train);
        root.getChildren().add(skip);
        root.setAlignment(Pos.CENTER);
        return new Scene(root,600,500);
    }

    private static Button makeButton(Player[] players, int x, Player[] selection, int x1, App app, Player chooser, String purpose, Model m) {
        if (players[x1] == null){
            return null;
        }
        Button p12 = new Button();
        p12.setText(players[x1].getName());
        p12.setOnAction(event -> {
            if(players.length >= x) selection[0] = players[x1];
            app.receive(chooser, selection[0], purpose, m);
        });
        return p12;
    }
}
