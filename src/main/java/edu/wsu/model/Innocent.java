package edu.wsu.model;

public class Innocent extends Player{
    //I thought it would be wise to split these off into different classes
    //rather than handling it all in one class
    //
    //That way, you can just add special functionality in each class rather than having to do
    //special checking within the player class

    @Override
    public void tellRole(){
        hear("You are an innocent passenger!");
    }

    public Innocent(String name){
        this.name = name;
    }
    public Innocent(){
        this.name = "Jimbo";//should not be called
    }
}
