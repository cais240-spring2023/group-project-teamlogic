package edu.wsu.model;

public class Silencer extends Murderer{
    public Silencer(String name) {
        super(name);
    }
    @Override
    public void tellRole(){
        hear("You are a silencer.");
    }

    @Override
    public String getNightActionName(){
        return "silence";
    }
    public boolean hasAction(){
        return true;
    }
    @Override
    public String roleString(){
        return "a silencer.";
    }
    @Override
    public void nightHandler(Player acted){
        acted.silence();
    }
}
