package edu.wsu.model;

import edu.wsu.controller.PlayerSelector;

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
        if(Model.TEXT_MODE) return textBasedActivityHandler(players);
        else return panelBasedActivityHandler(players);
    }

    public Player textBasedActivityHandler(Player[] players){
        System.out.println(name + ", select a player to investigate.\n");
        Player selected;
        while(true) {
            selected = textBasedPlayerSelector(players);
            if(selected == null) return null;
            if(selected.isAlive()) return selected;
        }
    }
    public Player panelBasedActivityHandler(Player[] players){
        return PlayerSelector.selectPlayer(players, name,"investigate", true);
    }

    @Override
    public String getNightActionName(){
        return "investigate";
    }
    public boolean hasAction(){
        return true;
    }
}
