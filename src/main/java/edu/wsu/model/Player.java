package edu.wsu.model;

public class Player {
    private String name;//What the player is called :)


    public Player(){//should never be called
        name = "Jimbo";
    }
    public Player(String name){//use this not Player()
        this.name = name;
    }

    public String tellRole(){//should never be called
        return "ERROR";
    }
}
