package edu.wsu;

import edu.wsu.model.Player;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    int currentPlayer = 0;


    ObservableList<Player> players;

    @Override
    public void start(Stage stage) throws Exception {
        // Create a 2x3 grid pane
        GridPane playerButtonGrid = new GridPane();
        playerButtonGrid.setHgap(10);
        playerButtonGrid.setVgap(10);
        playerButtonGrid.setPadding(new Insets(10));
        BorderPane basePane = new BorderPane();
        basePane.setCenter(playerButtonGrid);

        Player[] playerList = new Player[] {
            new Player("daph"),
            new Player("jason"),
            new Player("casey"),
            new Player("lucas"),
            new Player("ivan"),
            new Player("gavin")
        };
        players = FXCollections.observableList(Arrays.asList(playerList));

        //Loops to fill out the table
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < players.size()) {
                    Button button = new Button(players.get(index).getName());
                    playerButtonGrid.add(button, j, i);

                    //disables the button once picked to simulate it being voted out or killed
                    button.setOnAction(event -> {
                        button.setDisable(true);
                    });
                }
            }
        }
        ArrayList<String> names = new ArrayList<>();
        for (Player player:
             players) {
            names.add(player.getName());
        }
        ObservableList<String> currentPlayerOptions = FXCollections.observableList(names);
        ScrollPane optionsDisplay = new ScrollPane();
        ListView<String> options = new ListView<>();
        options.setItems(currentPlayerOptions);
        optionsDisplay.setContent(options);

        basePane.setRight(optionsDisplay);

        //code for the confirm button
        GridPane bottomPane = new GridPane();
        bottomPane.setAlignment(Pos.CENTER);

        Button confirmButton = new Button("confirm Turn");
        confirmButton.setOnAction(event -> {
            //add logic for player actions
            System.out.println("Turn has been confirmed");
        });
        bottomPane.add(confirmButton, 1, 1);
        basePane.setBottom(bottomPane);

        //Window maker
        Scene scene = new Scene(basePane, 400, 300);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Player List");
        stage.setScene(scene);
        stage.show();

    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}