package edu.wsu.model;

import edu.wsu.App;
import edu.wsu.controller.MessageDisplayerFX;
import edu.wsu.controller.PlayerSelector;

import java.util.ArrayList;
import java.util.Scanner;

public class Player implements PlayerInterface{
    protected ArrayList<String> actions;
    protected String name;//What the player is called :)
    private boolean alive;
    private Player killer;
    private Player visited;
    private String messages = "";
    private boolean input = true;//for testing
    private static App appLink;

    public String getNightActionName(){
        return "";
    }
    public boolean hasAction(){
        return false;
    }



    public Player(String name){//use this not Player()
        this.name = name;
        alive = true;
        this.actions = new ArrayList<>();
        actions.add("vote");
        actions.add("skip");
    }
    public static void setAppLink(App app){
        appLink = app;
    }
    public ArrayList<String> getActions() {
        return actions;
    }

    public void setActions(ArrayList<String> actions) {
        this.actions = actions;
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

    @Override
    public Player vote(Player[] players){//this MUST be a living player, add a check to make sure it only returns living players!
        if(Model.TEXT_MODE) return textVote(players);
        else return panelVote(players);
    }
    public Player textVote(Player[] players){
        System.out.println(name + ", select who to vote to kill.");
        Player selected;
        while(true) {
            selected = textBasedPlayerSelector(players);
            if(selected == null) return null;
            if(selected.isAlive()) return selected;
        }
    }
    public Player panelVote(Player[] players){
        return PlayerSelector.selectPlayer(players, name,"vote against", true);
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
    public static void clear(){
        for(int i = 0; i < 1000; i++) {
            System.out.println("");//trolled
        }
    }
    @Override
    public boolean displayMessages(Model m){
        if(!messages.equals("")) {
            if (Model.TEXT_MODE) textMessages();
            else panelMessages(m);
            clearMessages();
            return true;
        }
        else return false;
    }
    public void panelMessages(Model m){
        MessageDisplayerFX.display(name,messages,appLink, m);
    }
    public void textMessages(){//This should be replaced when FXML is working
        clear();
        System.out.println(name + ", your messages...\n\n");
        System.out.println(messages);
        if(input) {
            Scanner sc = new Scanner(System.in);
            System.out.println("\n\nPress ENTER to continue...");
            sc.nextLine();
        }
    }

    protected Player textBasedPlayerSelector(Player[] players){//This should be replaced when we have FXML
        Scanner sc = new Scanner(System.in);
        while(true) {
            String name = sc.nextLine();//Allows player to input a name
            if(name.equals("")){
                return null;
            }
            for(int i = 0; i < players.length; i++){
                if(players[i].nameIs(name)){//Identifies a player with this name
                    return players[i];//Success, this is our guy
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
