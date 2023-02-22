package edu.wsu.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Detective extends Innocent{
    //I thought it would be wise to split these off into different classes
    //rather than handling it all in one class
    //
    //That way, you can just add special functionality in each class rather than having to do
    //special checking within the player class

    public Detective(String name) {
        super(name);
        actions.add("arrest");
    }

    @Override
    public void tellRole(){
        hear("You are a detective.");
    }

    @Override
    public Player activityHandler(Player[] players){
        Player selected;
        while(true) {
            selected = selectPlayer(players);
            if(selected.isAlive()) return selected;
        }
    }
}
