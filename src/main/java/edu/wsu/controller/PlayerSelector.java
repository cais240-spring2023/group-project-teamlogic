package edu.wsu.controller;

import edu.wsu.App;
import edu.wsu.model.Model;
import edu.wsu.model.Player;

import static edu.wsu.view.PlayerSelectorFX.newScene;

public class PlayerSelector {
    public static void choose(Player[] players, Player chooser, String purpose, App app,Model m){
        app.changeScene(newScene(players,chooser,purpose,app, m));
    }
}
