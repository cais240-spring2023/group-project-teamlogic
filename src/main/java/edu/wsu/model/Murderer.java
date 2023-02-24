package edu.wsu.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Murderer extends Player{
    //I thought it would be wise to split these off into different classes
    //rather than handling it all in one class
    //
    //That way, you can just add special functionality in each class rather than having to do
    //special checking within the player class

    public Murderer(String name) {
        super(name);
        this.actions = new ArrayList<>();
        actions.add("vote");
        actions.add("skip");
        actions.add("murder");
    }

    @Override
    public void tellRole(){
        hear("You are the murderer!");
    }



    @Override
    public Player activityHandler(Player[] players){
        System.out.println(name + ", select a player to MURDER!\n");
        Player selected;
        while(true) {
            selected = selectPlayer(players);
            if(selected == null) return null;
            if(selected.isAlive()) return selected;
        }
    }
}
