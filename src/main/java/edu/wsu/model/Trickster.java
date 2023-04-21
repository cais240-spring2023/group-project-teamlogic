package edu.wsu.model;

import edu.wsu.model.Model.Role;

public class Trickster extends Player{
    public Trickster(String name) {
        super(name);
    }


    @Override
    public void tellRole(){
        hear("You are a sneaky trickster!");
    }
    @Override
    public String roleString(){
        return "a trickster.";
    }
    @Override
    public Role getRole() {
        return Role.TRICKSTER;
    }
    @Override
    public String roleName(){ return "Trickster";}
}
