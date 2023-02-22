package edu.wsu.model;

import java.util.Scanner;

public class Player implements PlayerInterface{
    protected String name;//What the player is called :)
    private boolean alive;
    private Player killer;
    private Player visited;
    private String messages = "";
    private boolean input = true;//for testing


    public Player(){//should never be called
        name = "Jimbo";
        alive = true;
    }
    public Player(String name){//use this not Player()
        this.name = name;
        alive = true;
    }

    public static Player create(){//This should be entirely replaced when we have FXML working
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        sc.close();
        return new Player(name);
    }
    @Override
    public void tellRole(){//should never be called
    }
    @Override
    public void revive(){
        alive = true;
    }
    @Override
    public void kill(){
        alive = false;
    }
    @Override
    public void killedBy(Player murderer){
        alive = false;
        killer = murderer;
    }
    @Override
    public boolean isAlive(){
        return alive;
    }
    //might need error handling if the selected player is dead.
    @Override
    public Player vote(Player[] players){//this MUST be a living player, add a check to make sure it only returns living players!
        Player selected;
        while(true) {
            selected = selectPlayer(players);
            if(selected.isAlive()) return selected;
        }
    }
    @Override
    public Player doActivity(Player[] players){
        visited = activityHandler(players);
        return visited;
    }
    @Override
    public Player activityHandler(Player[] players){
        return null;
    }
    @Override
    public boolean nameIs(String name){
        return this.name.equals(name);
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public Player getVisited(){
        return visited;
    }
    @Override
    public void hear(String message){
        if(messages.equals("")){
            messages = message;
        }
        else{
            messages = messages + "\n" + message;
        }
    }
    @Override
    public void clearMessages(){
        messages = "";
    }
    @Override
    public void displayMessages(){//This should be replaced when FXML is working
        System.out.println(name + ", your messages...\n\n");
        System.out.println(messages);
        if(input) {
            Scanner sc = new Scanner(System.in);
            System.out.println("\n\nPress ENTER to continue...");
            sc.next();
            sc.close();
        }
        clearMessages();
    }

    protected Player selectPlayer(Player[] players){//This should be replaced when we have FXML
        Scanner sc = new Scanner(System.in);
        while(true) {
            String name = sc.nextLine();//Allows player to input a name
            if(name.equals("")){
                return null;
            }
            for(int i = 0; i < players.length; i++){
                if(players[i].nameIs(name)){//Identifies a player with this name
                    sc.close();//Success, this is our guy
                    return players[i];
                }
            }
        }
    }

    @Override
    public Innocent setInnocent(){
        return new Innocent(name);
    }
    @Override
    public Murderer setMurderer(){
        return new Murderer(name);
    }
    @Override
    public Detective setDetective(){
        return new Detective(name);
    }
    @Override
    public void disableInput(){
        input = false;
    }
}
