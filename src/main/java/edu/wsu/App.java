package edu.wsu;

import edu.wsu.model.Detective;
import edu.wsu.model.Innocent;
import edu.wsu.model.Murderer;
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
import javafx.scene.control.Label;
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
    private BorderPane dayPane;
    private BorderPane transitionPane;
    private Label transition;
    private String selectedPlayer;
    private String time = "day";

    @Override
    public void start(Stage stage) throws Exception {
        // Create a 2x3 grid pane
        Player[] playerList = new Player[] {
                new Innocent("daph"),
                new Detective("jason"),
                new Murderer("casey"),
                new Innocent("lucas"),
                new Innocent("ivan"),
                new Innocent("gavin")
        };
        players = FXCollections.observableList(Arrays.asList(playerList));
        GridPane playerButtonGrid = new GridPane();
        playerButtonGrid.setHgap(10);
        playerButtonGrid.setVgap(10);
        playerButtonGrid.setPadding(new Insets(10));
        dayPane = new BorderPane();
        dayPane.setCenter(playerButtonGrid);
        transitionPane = new BorderPane();
        Button next = new Button("next");
        next.setOnAction(actionEvent -> {
            scene.setRoot(dayPane);
            ArrayList<String> actions = playerList[currentPlayer].getActions();
            ObservableList<String> currentPlayerOptions = FXCollections.observableList(actions);
            ListView<String> options = new ListView<>();
            options.setItems(currentPlayerOptions);
            ScrollPane optionsDisplay = new ScrollPane();
            optionsDisplay.setContent(options);
            ScrollPane child = (ScrollPane) dayPane.getChildren().get(1);
            ListView<String> grandChild = (ListView<String>) child.getContent();
            grandChild.setItems(currentPlayerOptions);
        });
        transition = new Label(players.get(currentPlayer).getName() + " step up");
        transitionPane.setCenter(next);
        transitionPane.setTop(transition);


        //Loops to fill out the table
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < players.size()) {
                    Button button = new Button(players.get(index).getName());
                    playerButtonGrid.add(button, j, i);

                    //disables the button once picked to simulate it being voted out or killed
                    button.setOnAction(event -> {
                        //button.setDisable(true);
                        selectedPlayer = players.get(index).getName();
                    });
                }
            }
        }
        ArrayList<String> actions = playerList[currentPlayer].getActions();
        ObservableList<String> currentPlayerOptions = FXCollections.observableList(actions);
        ScrollPane optionsDisplay = new ScrollPane();
        ListView<String> options = new ListView<>();
        options.setItems(currentPlayerOptions);
        optionsDisplay.setContent(options);
        dayPane.setRight(optionsDisplay);

        //code for the confirm button
        GridPane bottomPane = new GridPane();
        bottomPane.setAlignment(Pos.CENTER);

        Button confirmButton = new Button("confirm Turn");
        confirmButton.setOnAction(event -> {
            //add logic for player actions
            System.out.println("Turn has been confirmed");
            if (options.getSelectionModel().getSelectedItem() != null && selectedPlayer != null) {
                selectedPlayer = null;
                //needs to do action
                currentPlayer++;
                //needs to have current player connected to looping player in model
                Label transLabel = new Label(players.get(currentPlayer).getName() + " step up");
                transitionPane.setTop(transLabel);
                goToTransition();
            }
        });
        bottomPane.add(confirmButton, 1, 1);
        dayPane.setBottom(bottomPane);

        //Window maker
        scene = new Scene(transitionPane, 400, 300);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Player List");
        stage.setScene(scene);
        stage.show();

    }

    public void goToTransition() {
        scene.setRoot(transitionPane);
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