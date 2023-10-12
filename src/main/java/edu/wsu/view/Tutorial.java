package edu.wsu.view;

import edu.wsu.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class Tutorial {
    private static final String TUTORIAL = "\n\n\n\nThis is a social deduction game.\n" +
            "Every player will be assigned a role, and the goal of the game is to deduce which roles\n" +
            "are present in the game, and which players have which role.\n" +
            "Each player can take actions at night based on their role, and every afternoon they can\n" +
            "vote players they think are suspicious, or they think get in their way, off the train.\n\n\n" +
            "There are three teams: Innocents, Murderers, and Neutrals.\n\n" +
            "The innocents take actions which serve the greater good at night. They win when there are\n" +
            "no more murderers threatening them.\n\n" +
            "The murderers take actions which kill innocent passengers, or help other murderers get\n" +
            "away with their crimes. They win when there are no more innocent passengers left to stop\n" +
            "their reign of terror.\n\n" +
            "Neutral roles throw a wrench into the dichotomy, with special win conditions independent\n" +
            "of eliminating either side. They can win both with the murderers or the innocents.\n\n\n";

    private static final String ROLE_LIST = "ROLE LIST:\n\n" +
            "(I) NORMIE\n" +
            "The normie takes no action at night, but can use their deduction skills to help their team.\n\n" +
            "(I) DETECTIVE\n" +
            "The detective can look for clues at night. The next morning, they will know who the person\n" +
            "they visited, themselves visited.\n\n" +
            "(I) DOCTOR\n" +
            "The doctor can select a player to heal at night. Even if this player is attacked, they\n"+
            "will survive through the night.\n\n" +
            "(I) ENGINEER\n" +
            "If the engineer selects themselves at night, they will work on the train engine, causing\n"+
            "it to reach its destination faster, leading to an innocent victory.\n\n" +
            "(M) MURDERER\n" +
            "The murderer will kill one person at night.\n\n" +
            "(M) JANITOR\n" +
            "The janitor will \"clean\" one player each night. If a detective investigates a cleaned\n"+
            "player, they will collect no evidence.\n\n" +
            "(M) SILENCER\n" +
            "The silencer will select one player to silence each night. A silenced player can not vote.\n\n"+
            "(N) TRICKSTER\n" +
            "The trickster is a neutral role who wins if they deceive the innocent roles into voting\n"+
            "them off the train. If they are murdered, or if they survive the trip, they lose.";


    public static Scene newScene(App a){
        Label l = new Label(TUTORIAL);
        Label t = new Label(ROLE_LIST);
        t.setTextAlignment(TextAlignment.CENTER);
        HBox hbox = new HBox();
        hbox.getChildren().add(l);
        hbox.getChildren().add(t);
        hbox.setAlignment(Pos.TOP_CENTER);

        Button back = new Button("Return");
        back.setOnAction(event -> {a.changeScene(a.getDefaultScene());});
        back.setPrefWidth(450);
        VBox vbox = new VBox();
        vbox.getChildren().add(hbox);
        vbox.getChildren().add(back);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        return new Scene(vbox, 960,App.V1);
    }
}
