package edu.wsu.model;

import edu.wsu.model.Model.Role;

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
        return "silence, or ";
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
    @Override
    public Role getRole() {
        return Role.SILENCER;
    }
    @Override
    public String roleName(){ return "Silencer";}
}
