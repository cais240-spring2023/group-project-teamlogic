package edu.wsu;

import edu.wsu.controller.*;
import edu.wsu.model.Model;
import edu.wsu.model.Player;
import edu.wsu.view.MessageDisplayerFX;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private final String TUTORIAL = "\n\n\n\nThis is a social deduction game.\n" +
            "Every player will be assigned a role, and the goal of the game is to deduce which roles\n" +
            "are present in the game, and which players have which role.\n" +
            "Each play can take actions at night based on their role, and every afternoon they can\n" +
            "vote players they think are suspicious, or they think get in their way, off the train.\n\n\n" +
            "There are three teams: Innocents, Murderers, and Neutrals.\n\n" +
            "The innocents take actions which serve the greater good at night. They win when there are\n" +
            "no more murderers threatening them.\n\n" +
            "The murderers take actions which kill innocent passengers, or help other murderers get\n" +
            "away with their crimes. They win when there are no more innocent passengers left to stop\n" +
            "their reign of terror.\n\n" +
            "Neutral roles throw a wrench into the dichotomy, with special win conditions independent\n" +
            "of eliminating either side. They can win both with the murderers or the innocents.\n\n\n" +
            "ROLE LIST:\n\n" +
            "(I) NORMIE\n" +
            "The normie can take no action at night.\n\n" +
            "(I) DETECTIVE\n" +
            "The detective can look for clues at night. The next morning, they will know who the person\n" +
            "they visited, themselves visited.\n\n" +
            "(M) MURDERER\n" +
            "The murderer will kill one person at night.\n\n";

    private Scene currentlyShowing;
    private Stage stage;
    private Order currentlyOn;
    private boolean skipVoting = true;
    private Player whoseTurn = null;

    @Override
    public void start(Stage stage){
        final int BUTTON_WIDTH = 125;

        Image nestor = new Image("file:./src/main/resources/nestor.png");
        ImageView nestorView = new ImageView(nestor);

        Button hotSeat = new Button("Hot Seat");
        hotSeat.setPrefWidth(BUTTON_WIDTH);
        hotSeat.setOnAction(event -> {startGame();});

<<<<<<< Updated upstream
        Button server = new Button("Launch server");
        server.setPrefWidth(BUTTON_WIDTH);

        Button client = new Button("Connect to server");
        client.setPrefWidth(BUTTON_WIDTH);

        Button roleList = new Button("Tutorial");
        roleList.setPrefWidth(BUTTON_WIDTH);
        roleList.setOnAction(event -> {System.out.println(TUTORIAL);});

        Button debugMode = new Button("Debug Mode");
        debugMode.setPrefWidth(BUTTON_WIDTH);
        debugMode.setOnAction(event -> {
            DebugMode.debug(this);});



        BorderPane root = new BorderPane();
        VBox vbox = new VBox();
        vbox.getChildren().add(nestorView);
        vbox.getChildren().add(hotSeat);
        vbox.getChildren().add(server);
        vbox.getChildren().add(client);
        vbox.getChildren().add(roleList);
        vbox.getChildren().add(debugMode);
        vbox.setAlignment(Pos.CENTER);
        root.setCenter(vbox);

        Label nameLabel = new Label("Enter Name");
        TextField nameField = new TextField();
        Button submitButton = new Button("Submit");
        Button exitButton = new Button("Done");
        VBox root2 = new VBox(10, nameLabel, nameField, submitButton, exitButton);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 600, 500);

        stage.setTitle("Nestor's Murder Mystery");
=======
    ObservableList<Player> players;
    private BorderPane dayPane;
    private BorderPane transitionPane;
    private Label transition;
    private String selectedPlayer;
    private String time = "day";
    private int turnCount = 0;
    @Override
    public void start(Stage stage) throws Exception {
        // Create a 2x3 grid pane
        Player[] playerList = new Player[] {
                new Murderer("daph"),
                new Innocent("jason"),
                //new Murderer("casey"),
                new Innocent("lucas"),
                new Innocent("ivan"),
                //new Innocent("gavin")
        };
        game.addPlayer(playerList[0]);
        game.addPlayer(playerList[1]);
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
            if (currentPlayer >= playerList.length){
                if (time.equals("day")){
                    time = "night";
                }
                if (time.equals("night")){
                    time = "day";
                    turnCount++;
                }
                currentPlayer = 0;
            }
            ArrayList<String> actions = playerList[currentPlayer].getAction();
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
        ArrayList<String> actions = playerList[currentPlayer].getAction();
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
                if (currentPlayer>=playerList.length){
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
        dayPane.setBottom(bottomPane);

        //Window maker
        scene = new Scene(transitionPane, 400, 300);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Player List");
>>>>>>> Stashed changes
        stage.setScene(scene);
        currentlyShowing = scene;
        this.stage = stage;
        stage.show();


    }

    public void changeScene(Scene scene){
        if(currentlyShowing != null) stage.setScene(scene);
        currentlyShowing = scene;
    }


    public void startGame() {
        Model m = new Model();
        m.setAppLink(this);
        UsernameInput.namer(m,this); //this is where the new players should get the names
    }

    public void beginGame(Model model){
        model.assignRoles();
        model.tellRoles();
        currentlyOn = Order.GOOD_MORNING;
        doNext(model);
    }

    public void beginGameFromDebugging(Model model){

        model.tellRoles();
        currentlyOn = Order.GOOD_MORNING;
        doNext(model);
    }

    public enum Order{
        GOOD_MORNING,DISPLAY_MESSAGES,VOTE,THROW_OFF,NIGHT_ACTION,END,CLOSE
    }

    public void next(Model m){
        Model.Role winners;
        switch(currentlyOn){
            case GOOD_MORNING:
                currentlyOn = Order.DISPLAY_MESSAGES;
                whoseTurn = m.getNextPlayer(null);
                break;
            case DISPLAY_MESSAGES:
                whoseTurn = m.getNextPlayer(whoseTurn);
                if(whoseTurn == null){
                    if(skipVoting){
                        currentlyOn = Order.NIGHT_ACTION;
                        skipVoting = false;
                    }
                    else currentlyOn = Order.VOTE;
                    whoseTurn = m.getNextPlayer(null);
                }
                break;
            case VOTE:
                whoseTurn = m.getNextPlayer(whoseTurn);
                if(whoseTurn == null){
                    currentlyOn = Order.THROW_OFF;
                }
                break;
            case THROW_OFF:
                whoseTurn = m.getNextPlayer(null);
                currentlyOn = Order.NIGHT_ACTION;
                winners = m.checkWinner();
                if(winners != null) goodGame(winners, m);
                break;
            case NIGHT_ACTION:
                whoseTurn = m.getNextPlayer(whoseTurn);
                if(whoseTurn == null){
                    m.nightOver();
                    winners = m.checkWinner();
                    m.incrementTurn();
                    currentlyOn = Order.GOOD_MORNING;
                    if(winners != null) goodGame(winners, m);
                    if(m.getTurn() >= Model.MAX_TURNS) goodGame(Model.Role.INNOCENT,m);
                }
                break;
            case END:
                currentlyOn = Order.CLOSE;
                break;
        }
        switch(currentlyOn){
            case DISPLAY_MESSAGES:
                TransitionController.display(whoseTurn.getName(), "read your messages",this, m);
                break;
            case NIGHT_ACTION:
                TransitionController.display(whoseTurn.getName(),"prepare for bed",this, m);
                break;
            default:
                doNext(m);
        }
    }

    public void doNext(Model m){
        switch(currentlyOn){
            case GOOD_MORNING:
                System.out.println("Good morning");
                goodMorning(m);
                break;
            case DISPLAY_MESSAGES:
                System.out.println("Display messages");
                displayMessages(whoseTurn, m);
                break;
            case VOTE:
                System.out.println("Vote");
                getVote(whoseTurn, m);
                break;
            case THROW_OFF:
                System.out.println("Throw off");
                Player victim = m.tallyVotes();
                if(victim != null) {
                    victim.kill();
                    victim.onMorning();//Sets deadFor to 1 so that the doctor can't save them
                    thrownOff(victim, m);
                }
                break;
            case NIGHT_ACTION:
                System.out.println("Night action");
                getNightAction(whoseTurn, m);
                break;
            case CLOSE: Platform.exit();
        }
    }

    public void goodMorning(Model m){
        String goodMorning = "Good morning!\nLiving Players: " + m.listLivingPlayers();
        MessageDisplayerFX.display("Day "+m.getTurn(),goodMorning,this, m);
        m.onMorning();
    }
    public void displayMessages(Player player, Model m){
        if(player.displayMessages(m));
        else next(m);
    }
    public void getVote(Player player, Model m){
        PlayerSelector.choose(m.getPlayers(),player,"vote against",this,m);
    }
    public void receive(Player player, Player choice, String purpose, Model m){
        if(purpose == "vote against") m.receiveVote(player, choice);
        else{
            m.submitAction(player,choice);
            player.visited(choice);
        }
        next(m);
    }
    public void getNightAction(Player player, Model m){
        if(player.hasAction()) PlayerSelector.choose(m.getPlayers(),player,player.getNightActionName(),this, m);
        else MessageDisplayerFX.display(player.getName(),"You sleep soundly in your cabin... hopefully you wake up tomorrow!",this,m);
    }
    public void goodGame(Model.Role winners, Model m){
        System.out.println("Good game");
        currentlyOn = Order.END;
        String winnerString = "";
        switch(winners){
            case INNOCENT:
                winnerString = "Innocents";
                break;
            case MURDERER:
                winnerString = "Murderers";
                break;
            case NONE:
                winnerString = "Nobody";
        }
        winnerString += " won!";
        MessageDisplayerFX.display("Winners",winnerString,this, m);
        System.out.println(winnerString);
    }
    public void thrownOff(Player player, Model m){
        MessageDisplayerFX.display("Player killed!",player.getName() + " was thrown off the train. Good luck to them!\n\nThey were " + player.roleString(),this, m);
    }
}
