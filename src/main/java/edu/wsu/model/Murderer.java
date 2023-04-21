package edu.wsu.model;

import edu.wsu.model.Model.Role;
import java.util.ArrayList;

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
    public void nightHandler(Player acted){
        acted.killedBy(this);
    }

    public Player textBasedActivityHandler(Player[] players){
        System.out.println(name + ", select a player to MURDER!\n");
        Player selected;
        while(true) {
            selected = textBasedPlayerSelector(players);
            if(selected == null) return null;
            if(selected.isAlive()) return selected;
        }
    }
    public Player panelBasedActivityHandler(Player[] players){
        return null;
    }

    @Override
    public String getNightActionName(){
        return "murder, or ";
    }
    public boolean hasAction(){
        return true;
    }
    @Override
    public String roleString(){
        return "a murderer!";
    }

    public Role getRole(){
        return Role.MURDERER;
    }
}
