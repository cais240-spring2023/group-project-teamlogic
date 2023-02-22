package edu.wsu.model;

import java.util.ArrayList;

public class Innocent extends Player{

    //I thought it would be wise to split these off into different classes
    //rather than handling it all in one class
    //
    //That way, you can just add special functionality in each class rather than having to do
    //special checking within the player class
    protected ArrayList<String> actions;

    public Innocent(String name) {
        super(name);
        this.actions = new ArrayList<>();
        actions.add("vote");
        actions.add("skip");
    }
    public ArrayList<String> getActions() {
        return actions;
    }

    public void setActions(ArrayList<String> actions) {
        this.actions = actions;
    }

    @Override
    public void tellRole(){
        hear("You are an innocent passenger!");
    }
}

