package edu.wsu.model;

import java.util.Scanner;

public class Player {
    private String name;//What the player is called :)
    private boolean alive;
    private Player killer;
    private Player visited;
    private String messages;


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

    public void tellRole(){//should never be called
    }
    public void revive(){
        alive = true;
    }
    public void kill(){
        alive = false;
    }
    public void kill(Player killer){
        alive = false;
        this.killer = killer;
    }
    public boolean isAlive(){
        return alive;
    }

    public Player vote(Player[] players){//this MUST be a living player, add a check to make sure it only returns living players!
        Player selected;
        while(true) {
            selected = selectPlayer(players);
            if(selected.isAlive()) return selected;
        }
    }
    public Player doActivity(Player[] players){
        visited = activityHandler(players);
        return visited;
    }
    public Player activityHandler(Player[] players){
        return null;
    }
    public boolean nameIs(String name){
        return this.name.equals(name);
    }
    public String getName(){
        return name;
    }
    public Player getVisited(){
        return visited;
    }
    public void hear(String message){
        if(messages.equals("")){
            messages = message;
        }
        else{
            messages = messages + "\n" + message;
        }
    }
    private void clearMessages(){
        messages = "";
    }
    public void displayMessages(){//This should be replaced when FXML is working
        System.out.println(name + ", your messages...\n\n");
        System.out.println(messages);
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\nPress ENTER to continue...");
        sc.next();
        sc.close();
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
}
