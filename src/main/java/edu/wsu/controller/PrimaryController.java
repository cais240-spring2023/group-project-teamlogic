package edu.wsu.controller;
import edu.wsu.App;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//This is where Im going to put the java fx code for now

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;


public class PrimaryController extends Application {
    public static ArrayList<String> names = new ArrayList<>();
    @Override
    public void start(Stage mainStage) throws Exception{


            TableView<String> playerTable = new TableView<>();
            TableColumn<String, String> nameCol = new TableColumn<>("Name");
            playerTable.getColumns().add(nameCol);

            ObservableList<String> players = playerTable.getItems();
            players.addAll(names);

            BorderPane bp = new BorderPane(playerTable);
            Scene mainScene = new Scene(bp, 200, 200);
            mainStage.setScene(mainScene);

            Button showNames = new Button("New Names");
            showNames.setOnAction(event -> {
                players.setAll(names);
                mainStage.show();
            });
        //code for the skip button
        GridPane bottomPane = new GridPane();
        bottomPane.setAlignment(Pos.CENTER);
        Button bottomButton = new Button("Skip Turn");
        bottomButton.setOnAction(event -> {
            System.out.println("Turn has been skipped");
        });

        Stage nameStage = new Stage();

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            String insert = nameField.getText();
            if (!insert.isEmpty()) {
                names.add(insert);
                nameField.clear();
            }
        });

        VBox vBox = new VBox(nameLabel, nameField, addButton);
        Scene scene = new Scene(vBox, 200, 200);
        nameStage.setScene(scene);
        nameStage.show();

    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
