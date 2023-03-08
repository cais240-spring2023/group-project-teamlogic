package edu.wsu;


import edu.wsu.model.*;
import javafx.application.Application;
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

import static edu.wsu.model.Model.MAX_TURNS;

/**
 * JavaFX App
 */
public class OldApp extends Application {

    private static Scene scene;


    int currentPlayer = 0;
    Model game = new Model();


    ObservableList<Player> players;
    private BorderPane dayPane;
    private BorderPane transitionPane;
    private Label transition;
    private String selectedPlayer;
    private String time = "night";
    private int turnCount = 0;

    public OldApp() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        Player[] playerList = new Player[] {
                new Murderer("daph"),
                new Innocent("jason"),
                new Innocent("casey"),
                new Innocent("lucas"),
                new Innocent("ivan"),
                new Detective("gavin")
        };
        game.addPlayer(playerList[0]);
        game.addPlayer(playerList[1]);
        players = FXCollections.observableList(Arrays.asList(playerList));
        GridPane playerButtonGrid = new GridPane();
        playerButtonGrid.setHgap(10);
        playerButtonGrid.setVgap(10);
        playerButtonGrid.setPadding(new Insets(10));
        Label timeOfDay = new Label(time);
        dayPane = new BorderPane();
        dayPane.setRight(timeOfDay);
        dayPane.setCenter(playerButtonGrid);
        transitionPane = new BorderPane();
        Button next = new Button("next");
        next.setOnAction(actionEvent -> {
            makeButtonGrid(playerButtonGrid);
            scene.setRoot(dayPane);
            System.out.println(currentPlayer + ", " + playerList.length);
//            ArrayList<String> actions = playerList[currentPlayer].getActions();
//            ObservableList<String> currentPlayerOptions = FXCollections.observableList(actions);
//            ListView<String> options = new ListView<>();
//            options.setItems(currentPlayerOptions);
//            ScrollPane optionsDisplay = new ScrollPane();
//            optionsDisplay.setContent(options);
            //ScrollPane child = (ScrollPane) dayPane.getChildren().get(1);
            //ListView<String> grandChild = (ListView<String>) child.getContent();
            //grandChild.setItems(currentPlayerOptions);
        });
        transition = new Label(players.get(currentPlayer).getName() + " step up");
        transitionPane.setCenter(next);
        transitionPane.setTop(transition);



        //Loops to fill out the table
        makeButtonGrid(playerButtonGrid);
        ArrayList<String> actions = playerList[currentPlayer].getActions();
        ObservableList<String> currentPlayerOptions = FXCollections.observableList(actions);
        ScrollPane optionsDisplay = new ScrollPane();
        ListView<String> options = new ListView<>();
        options.setItems(currentPlayerOptions);
        optionsDisplay.setContent(options);
        //dayPane.setRight(optionsDisplay);


        //code for the confirm button
        GridPane bottomPane = new GridPane();
        bottomPane.setAlignment(Pos.CENTER);

        Button confirmButton = new Button("confirm Turn");
        confirmButton.setOnAction(event -> {
            //add logic for player actions
            System.out.println("Turn has been confirmed");
            if (options.getSelectionModel().getSelectedItem() != null && selectedPlayer != null) {
                String action = options.getSelectionModel().getSelectedItem();
                if (action.equals("murder")){
                    for (Player player:
                         game.players) {
                        if (player.nameIs(selectedPlayer)){
                            player.kill();
                        }
                    }
                }
                if (action.equals("vote")){
                    game.receiveVote(players.get(currentPlayer), game.getPlayer(selectedPlayer));
                }
                selectedPlayer = null;
                //needs to do action
                currentPlayer++;
                if (currentPlayer <= playerList.length){
                    turnCount++;
                    Player killMe = game.tallyVotes();
                    if (killMe != null){
                        System.out.println("die");
                        killMe.kill();
                    }
                }
                if (turnCount >= MAX_TURNS){
                    Model.Role winner = game.checkWinner();
                    BorderPane victoryPane = new BorderPane();
                    Label vMessage = new Label(winner.name());
                    victoryPane.setCenter(vMessage);
                    scene.setRoot(victoryPane);
                    return;
                }
                //needs to have current player connected to looping player in model
                Label transLabel = new Label(players.get(currentPlayer).getName() + " step up");
                transitionPane.setTop(transLabel);
                goToTransition();
            }
        });
        bottomPane.add(confirmButton, 1, 1);
        //dayPane.setBottom(bottomPane);

        //Window maker
        scene = new Scene(transitionPane, 400, 300);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Player List");
        stage.setScene(scene);
        stage.show();

    }

    private void cycleTime() {
        //change day night label
        if (time.equals("day")){
            time = "night";
        }
        if (time.equals("night")){
            time = "day";
            turnCount++;
        }
        currentPlayer = 0;
    }

    private void makeButtonGrid(GridPane playerButtonGrid) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if (index < players.size()) {
                    System.out.println(players.get(index).isAlive());
                    Button button = new Button(players.get(index).getName());
                    if (!players.get(index).isAlive()){
                        button.setDisable(true);
                    }
                    playerButtonGrid.add(button, j, i);
                    //disables the button once picked to simulate it being voted out or killed
                    button.setOnAction(event -> {
                        if (time.equals("night")){
                            if (players.get(currentPlayer) instanceof Murderer){
                                for (Player current:
                                     players) {
                                    if (current.getName().equals(players.get(index).getName())) {
                                        current.kill();
                                        current.killedBy(players.get(currentPlayer));
                                    }
                                }
                                goToTransition();
                            }
                            else {
                                goToTransition();
                            }
                        }
                        if (currentPlayer >= players.size()){
                            cycleTime();
                        }

                        //button.setDisable(true);
                        selectedPlayer = players.get(index).getName();
                    });
                }
            }
        }
    }

    public void goToTransition() {
        currentPlayer++;
        scene.setRoot(transitionPane);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OldApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}