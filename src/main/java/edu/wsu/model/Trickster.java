package edu.wsu.model;

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
}
