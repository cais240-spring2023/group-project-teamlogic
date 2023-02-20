package edu.wsu;

import edu.wsu.model.Players;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    int currentPlayer = 0;


    ObservableList<Players> players;

    @Override
    public void start(Stage stage) throws Exception {
        // Create a 2x3 grid pane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        List<Players> playerList = Arrays.asList(
                new Players("innocent", "daph"),
                new Players("murderer", "jason"),
                new Players("cop", "casey"),
                new Players("cop", "lucas"),
                new Players("cop", "ivan"),
                new Players("cop", "gavin")
        );
        players = FXCollections.observableList(playerList);

        //Loops to fill out the table
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < players.size()) {
                    Button button = new Button(players.get(index).playername);
                    gridPane.add(button, j, i);

                    //disables the button once picked to simulate it being voted out or killed
                    button.setOnAction(event -> {
                        button.setDisable(true);
                    });
                }
            }
        }
        ObservableList<String> currentPlayerOptions = FXCollections.observableList(players.get(currentPlayer).actionOption);
        ScrollPane optionsDisplay = new ScrollPane();
        ListView<String> options = new ListView<>();
        options.setItems(currentPlayerOptions);
        optionsDisplay.setContent(options);

        gridPane.add(optionsDisplay, 1,1);

        //code for the skip button
        GridPane bottomPane = new GridPane();
        bottomPane.setAlignment(Pos.CENTER);

        Button bottomButton = new Button("Skip Turn");
        bottomButton.setOnAction(event -> {
            System.out.println("Turn has been skipped");
        });

        //Window maker
        Scene scene = new Scene(gridPane, 400, 300);
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