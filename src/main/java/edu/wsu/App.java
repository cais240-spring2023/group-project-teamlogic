package edu.wsu;

import edu.wsu.controller.*;
import edu.wsu.model.Model;
import edu.wsu.model.ModelSingleton;
import edu.wsu.model.Player;
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

        Button roleList = new Button("View Role List");
        roleList.setPrefWidth(BUTTON_WIDTH);

        Button debugMode = new Button("Debug Mode");
        debugMode.setPrefWidth(BUTTON_WIDTH);
        debugMode.setOnAction(event -> {DebugMode.debug(this);});



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

        Scene scene = new Scene(root, 400, 350);

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
                    winners = model.checkWinner();
                    currentlyOn = Order.THROW_OFF;
                    if(winners != null) goodGame(winners);
                }
                break;
            case THROW_OFF:
                whoseTurn = model.getNextPlayer(null);
                currentlyOn = Order.NIGHT_ACTION;
                break;
            case NIGHT_ACTION:
                whoseTurn = model.getNextPlayer(whoseTurn);
                if(whoseTurn == null){
                    model.nightOver();
                    winners = model.checkWinner();
                    model.incrementTurn();
                    currentlyOn = Order.GOOD_MORNING;
                    if(winners != null) goodGame(winners);
                }
                break;
            case END:
                currentlyOn = Order.CLOSE;
                break;
        }
        switch(currentlyOn){
            case DISPLAY_MESSAGES: case NIGHT_ACTION:

                TransitionController.display(whoseTurn.getName(),this);
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
    }
    public void displayMessages(Player player){
        if(player.displayMessages(model));
        else next();
    }
    public void getVote(Player player){
        PlayerSelectorFX.choose(model.getPlayers(),player,"vote against",this,model);
    }
    public void receive(Player player, Player choice, String purpose){
        if(purpose == "vote against") model.receiveVote(player, choice);
        else model.submitAction(player,choice);
        next();
    }
    public void getNightAction(Player player){
        if(player.hasAction()) PlayerSelectorFX.choose(model.getPlayers(),player,player.getNightActionName(),this, model);
        else next();
    }
    public void goodGame(Model.Role winners){
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
        MessageDisplayerFX.display("Winners",winnerString,this, model);
        System.out.println(winnerString);
    }
    public void thrownOff(Player player){
        MessageDisplayerFX.display("Player killed!",player.getName() + " was thrown off the train. Good luck to them!\n\nThey were " + player.roleString(),this, model);
    }
}
