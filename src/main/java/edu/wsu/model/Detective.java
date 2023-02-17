package edu.wsu.model;

public class Detective extends Innocent{
    //I thought it would be wise to split these off into different classes
    //rather than handling it all in one class
    //
    //That way, you can just add special functionality in each class rather than having to do
    //special checking within the player class

    @Override
    public String tellRole(){
        return "You are a detective.";
    }
}
