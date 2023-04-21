package edu.wsu.model;

import edu.wsu.model.Model.Role;

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
    @Override
    public Role getRole(){
        return Role.JANITOR;
    }
    public String roleName(){ return "Janitor";}
}
