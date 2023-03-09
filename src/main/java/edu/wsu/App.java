package edu.wsu;

import edu.wsu.controller.MessageDisplayerFX;
import edu.wsu.controller.PlayerSelectorFX;
import edu.wsu.controller.UsernameInput;
import edu.wsu.model.Model;
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

    @Override
    public void start(Stage stage){
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



        BorderPane root = new BorderPane();
        VBox vbox = new VBox();
        vbox.getChildren().add(nestorView);
        vbox.getChildren().add(hotSeat);
        vbox.getChildren().add(server);
        vbox.getChildren().add(client);
        vbox.getChildren().add(roleList);
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
                    winners = m.checkWinner();
                    currentlyOn = Order.THROW_OFF;
                    if(winners != null) goodGame(winners, m);
                }
                break;
            case THROW_OFF:
                whoseTurn = m.getNextPlayer(null);
                currentlyOn = Order.NIGHT_ACTION;
            case NIGHT_ACTION:
                whoseTurn = m.getNextPlayer(whoseTurn);
                if(whoseTurn == null){
                    m.nightOver();
                    winners = m.checkWinner();
                    m.incrementTurn();
                    currentlyOn = Order.GOOD_MORNING;
                    if(winners != null) goodGame(winners, m);
                }
                break;
            case END:
                currentlyOn = Order.CLOSE;
                break;
        }
        doNext(m);
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
    }
    public void displayMessages(Player player, Model m){
        if(player.displayMessages(m));
        else next(m);
    }
    public void getVote(Player player, Model m){
        PlayerSelectorFX.choose(m.getPlayers(),player,"vote against",this,m);
    }
    public void receive(Player player, Player choice, String purpose, Model m){
        if(purpose == "vote against") m.receiveVote(player, choice);
        else m.submitAction(player,choice);
        next(m);
    }
    public void getNightAction(Player player, Model m){
        if(player.hasAction()) PlayerSelectorFX.choose(m.getPlayers(),player,player.getNightActionName(),this, m);
        else next(m);
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
        MessageDisplayerFX.display("Player killed!",player.getName() + " was thrown off the train. Good luck to them!",this, m);
    }
}
