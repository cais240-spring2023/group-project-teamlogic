package edu.wsu.model;

import edu.wsu.controller.PlayerSelector;

public class Silencer extends Murderer{
    public Silencer(String name) {
        super(name);
        actions.add("silenced");
    }
    @Override
    public void tellRole(){
        hear("You are a silencer.");
    }
    @Override
    public Player activityHandler(Player[] players){
        if(Model.TEXT_MODE) return textBasedActivityHandler(players);
        else return panelBasedActivityHandler(players);
    }

    public Player textBasedActivityHandler(Player[] players){
        System.out.println(name + ", select a player to silence.\n");
        Player selected;
        while(true) {
            selected = textBasedPlayerSelector(players);
            if(selected == null) return null;
            if(selected.isAlive()) return selected;
        }
    }

    public Player panelBasedActivityHandler(Player[] players){
        return PlayerSelector.selectPlayer(players, name,"silence", true);
    }

    @Override
    public String getNightActionName(){
        return "silence";
    }
    public boolean hasAction(){
        return true;
    }
}
