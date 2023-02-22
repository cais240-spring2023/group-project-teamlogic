package edu.wsu.model;

import java.util.Scanner;

public class Detective extends Innocent{
    //I thought it would be wise to split these off into different classes
    //rather than handling it all in one class
    //
    //That way, you can just add special functionality in each class rather than having to do
    //special checking within the player class

    @Override
    public void tellRole(){
        hear("You are a detective.");
    }

    public Detective(String name){
        this.name = name;
    }
    public Detective(){
        this.name = "Jimbo";//should not be called
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
