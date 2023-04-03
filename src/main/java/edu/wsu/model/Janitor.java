package edu.wsu.model;

public class Janitor extends Murderer{
    public Janitor(String name) {
        super(name);
    }
    @Override
    public void tellRole(){
        hear("You are a janitor.");
    }

    @Override
    public String getNightActionName(){
        return "clean, or ";
    }
    public boolean hasAction(){
        return true;
    }
    @Override
    public String roleString(){
        return "a janitor.";
    }
    @Override
    public void nightHandler(Player acted){
        acted.clean();
    }
}
