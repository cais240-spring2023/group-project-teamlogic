package edu.wsu;

import edu.wsu.controller.*;
import edu.wsu.model.Model;
import edu.wsu.model.ModelSingleton;
import edu.wsu.model.Player;
import edu.wsu.model.Trickster;
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

    public boolean DEBUG_MODE = false;

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
    private Model model;

    @Override
    public void start(Stage stage){
        initializeModel();
        final int BUTTON_WIDTH = 125;

        Image nestor = new Image("file:./src/main/resources/nestor.png");
        ImageView nestorView = new ImageView(nestor);


        Button hotSeat = new Button("Hot Seat");
        hotSeat.setPrefWidth(BUTTON_WIDTH);
        hotSeat.setOnAction(event -> {startGame();});

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
            DEBUG_MODE = true;
            DebugMode.debug(this);
        });



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
        scene.getStylesheets().addAll(this.getClass().getResource("/main/background.css").toExternalForm());
        stage.setTitle("Nestor's Murder Mystery");
        stage.setScene(scene);
        currentlyShowing = scene;
        this.stage = stage;
        stage.show();


    }

    public void initializeModel(){
        model = ModelSingleton.getInstance();
    }


    public void changeScene(Scene scene){
        if(currentlyShowing != null) stage.setScene(scene);
        currentlyShowing = scene;
    }


    public void startGame() {
        model.setAppLink(this);
        UsernameInput.namer(model,this); //this is where the new players should get the names
    }

    public void beginGame(){
        model.assignRoles();
        model.tellRoles();
        currentlyOn = Order.GOOD_MORNING;
        doNext();
    }

    public void beginGameFromDebugging(){
        model.tellRoles();
        currentlyOn = Order.GOOD_MORNING;
        doNext();
    }

    public enum Order{
        GOOD_MORNING,DISPLAY_MESSAGES,VOTE,THROW_OFF,NIGHT_ACTION,END,CLOSE
    }

    public void next(){
        Model.Role winners;
        switch(currentlyOn){
            case GOOD_MORNING:
                currentlyOn = Order.DISPLAY_MESSAGES;
                whoseTurn = model.getNextPlayer(null);
                break;
            case DISPLAY_MESSAGES:
                whoseTurn = model.getNextPlayer(whoseTurn);
                if(whoseTurn == null){
                    if(skipVoting){
                        currentlyOn = Order.NIGHT_ACTION;
                        skipVoting = false;
                    }
                    else currentlyOn = Order.VOTE;
                    whoseTurn = model.getNextPlayer(null);
                }
                break;
            case VOTE:
                whoseTurn = model.getNextPlayer(whoseTurn);
                if(whoseTurn == null){
                    currentlyOn = Order.THROW_OFF;
                }
                break;
            case THROW_OFF:
                whoseTurn = model.getNextPlayer(null);
                currentlyOn = Order.NIGHT_ACTION;
                winners = model.checkWinner();
                if(winners != null) goodGame();
                break;
            case NIGHT_ACTION:
                whoseTurn = model.getNextPlayer(whoseTurn);
                if(whoseTurn == null){
                    model.nightOver();
                    winners = model.checkWinner();
                    model.incrementTurn();
                    currentlyOn = Order.GOOD_MORNING;
                    if(winners != null) goodGame();
                    if(model.getTurn() >= Model.MAX_TURNS){
                        model.innocentsWon();
                        goodGame();
                    }
                }
                break;
            case END:
                currentlyOn = Order.CLOSE;
                break;
        }
        switch(currentlyOn){
            case DISPLAY_MESSAGES:
                TransitionController.display(whoseTurn.getName(), "read your messages",this);
                break;
            case NIGHT_ACTION:
                TransitionController.display(whoseTurn.getName(),"prepare for bed",this);
                break;
            default:
                doNext();
        }
    }

    public void doNext(){
        switch(currentlyOn){
            case GOOD_MORNING:
                System.out.println("Good morning");
                goodMorning();
                break;
            case DISPLAY_MESSAGES:
                System.out.println("Display messages");
                displayMessages(whoseTurn);
                break;
            case VOTE:
                System.out.println("Vote");
                getVote(whoseTurn);
                break;
            case THROW_OFF:
                System.out.println("Throw off");
                Player victim = model.tallyVotes();
                if(victim != null) {
                    victim.kill();
                    victim.onMorning();//Sets deadFor to 1 so that the doctor can't save them
                    if(victim instanceof Trickster) model.addWinner(victim);
                    thrownOff(victim);
                }
                break;
            case NIGHT_ACTION:
                System.out.println("Night action");
                getNightAction(whoseTurn);
                break;
            case CLOSE: Platform.exit();
        }
    }

    public void goodMorning(){
        String goodMorning = "Good morning!\nLiving Players: " + model.listLivingPlayers();
        MessageDisplayerFX.display("Day "+model.getTurn(),goodMorning,this, model);
        model.onMorning();
    }
    public void displayMessages(Player player){
        if(player.displayMessages(model));
        else next();
    }
    public void getVote(Player player){
        if(player.isSilenced()) MessageDisplayerFX.display(player.getName(), "You have been silenced.",this,model);
        else PlayerSelector.choose(model.getPlayers(),player,"vote against",this);
    }
    public void receive(Player player, Player choice, String purpose){
        if(purpose.equals("vote against")) model.receiveVote(player, choice);
        else{
            model.submitAction(player,choice);
            player.visited(choice);
        }
        next();
    }
    public void getNightAction(Player player){
        PlayerSelector.choose(model.getPlayers(),player,player.getNightActionName(),this);
    }
    public void goodGame(){
        System.out.println("Good game");
        currentlyOn = Order.END;
        String winnerString = "";
        Player[] winners = model.getWinners();
        for(int i = 0; i < winners.length; i++){
            if(winners[i] != null){
                if(i != 0){
                    winnerString += ", ";
                    if(i+1 >= winners.length || winners[i+1] == null){
                        winnerString += "and ";
                    }
                }
                winnerString += winners[i].getName();
            }
        }
        winnerString += " won!";
        MessageDisplayerFX.display("Winners",winnerString,this, model);
        System.out.println(winnerString);
    }
    public void thrownOff(Player player){
        MessageDisplayerFX.display("Player killed!",player.getName() + " was thrown off the train. Good luck to them!\n\nThey were " + player.roleString(),this, model);
    }
}
