package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Model;
import edu.wsu.model.ModelSingleton;
import edu.wsu.model.Player;

import static edu.wsu.view.PlayerSelectorFX.newScene;

public class PlayerSelector {
    public static void choose(Player[] players, Player chooser, String purpose, App app){
        app.changeScene(newScene(players,chooser,purpose,app, ModelSingleton.getInstance()));
    }
    public static void sendMessage(Player toPlayer, Player fromPlayer, String message, App app){
        toPlayer.hear(message);
        fromPlayer.visited(toPlayer);
        app.next();
    }
}
