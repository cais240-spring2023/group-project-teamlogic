package edu.wsu.model;

public class Silencer extends Murderer {
    public Silencer(String name) {
        super(name);
    }

    //explain silenced
    //tell the other player they were silenced
    @Override
    public void tellRole() {
        hear("You are a silencer.");
    }

    @Override
    public String roleString(){
        return "a silencer";
    }
    @Override
    public String getNightActionName(){
        return "silence";
    }
    public boolean hasAction(){
        return true;
    }

    public static String silencedString(){ return "an unseen force stops your tongue.";}

    @Override
    public void nightHandler(Player acted) {
    acted.silencedBy(this);
    acted.hear(Silencer.silencedString());
    }

}
