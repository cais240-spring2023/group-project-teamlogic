package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Model;
import edu.wsu.model.ModelSingleton;
import edu.wsu.model.Player;

import edu.wsu.view.PlayerSelectorFX;
import edu.wsu.view.PlayerSelectorPics;

public class PlayerSelector {
    public static void choose(Player[] players, Player chooser, String purpose, App app){
        if(app.DEBUG_MODE) app.changeScene(PlayerSelectorFX.newScene(players,chooser,purpose,app, ModelSingleton.getInstance()));
        else app.changeScene(PlayerSelectorPics.newScene(players,chooser,purpose,app,ModelSingleton.getInstance()));
    }
    public static void sendMessage(Player toPlayer, Player fromPlayer, String message, App app){
        toPlayer.hear(message);
        fromPlayer.visited(toPlayer);
        app.next();
    }
}
