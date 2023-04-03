package edu.wsu.model;

import edu.wsu.App;
import edu.wsu.view.MessageDisplayerFX;

import java.util.ArrayList;
import java.util.Scanner;

public class Player implements PlayerInterface{
    protected ArrayList<String> actions;
    protected String name;//What the player is called :)
    private boolean alive;
    private int deadFor = 0;//0 if alive, or during the night they got murdered. Increments by 1 every morning they're dead.
    private Player killer;
    private Player visited;
    private final String NO_MAIL = "You have no mail.";
    private String messages = "You have no mail.";
    private boolean input = true;//for testing
    private static App appLink;
    private boolean isImmune = false;//If the model handles the doctor healing you before
    private boolean cleaned = false;
    private int silenced = 0;

    public String getNightActionName(){
        return "";
    }
    public boolean hasAction(){
        return false;
    }
    public void visited(Player p){
        if(!cleaned) visited = p;
    }
    public void clean(){
        visited = null;
        cleaned = true;
    }
    public void silence(){
        silenced = 2;
    }
    public boolean isSilenced(){
        return silenced != 0;
    }

    public void onMorning(){
        if(!isAlive()) deadFor++;
        isImmune = false;
        cleaned = false;
        if(silenced != 0) silenced--;//Silenced will be set to 2, and then subtracted each morning. That way it clears
    }//After a full day of being silenced.
    public boolean justDied(){
        return deadFor == 0 && !alive;
    }
    public void setImmune(){
        isImmune = true;
    }
    public boolean isImmune(){
        return isImmune;
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
        System.out.println(name + "is immune? " + isImmune);
        if(!isImmune) {
            System.out.println(name + "is immune? TRUE");
            alive = false;
            killer = murderer;
        }
        else hear(Doctor.healString());
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
        return null;
    }
    @Override
    public Player doActivity(Player[] players){
        return null;
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
        if(messages.equals(NO_MAIL)){
            messages = message;
        }
        else{
            messages = messages + "\n" + message;
        }
    }
    @Override
    public void clearMessages(){
        messages = "You have no mail.";
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
        messages = messages.replace("$",Integer.toString(Model.MAX_TURNS-Model.m.getTurn()));
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
    public Doctor setDoctor(){
        return new Doctor(name);
    }
    public Engineer setEngineer(){
        return new Engineer(name);
    }
    public Janitor setJanitor() { return new Janitor(name);}
    @Override
    public void disableInput(){
        input = false;
    }
    public String roleString(){
        return "nobody!";
    }
    public void nightHandler(Player acted){
        //do nothing
    }
}
