package edu.wsu.model;

import edu.wsu.controller.PrimaryController;

import java.util.Random;
import java.util.Scanner;


public class Model
{

    public Player[] players;
    private static final int PLAYER_COUNT = 6;
    public static final int MAX_TURNS = 30;
    public Murderer murderer;//pointer to one of the players
    public Detective detective;//pointer also
    public boolean rolesAssigned;//keeps track of whether the roles were already assigned
    //                             because I can't directly access it the way I stored this information

    private Player[] votes = new Player[PLAYER_COUNT];//keeps track of each player's vote
    //index i -> player i's vote
    //e.g. votes[0] = Joebob, player 0 voted for Joebob



    public enum Role{
        NONE, INNOCENT, MURDERER, DETECTIVE;
    }

    public Model() {
        String[] names = new String[]{"joe", "tim", "bob", "alan", "kenneth", "mari"};
        boolean testMode = false;
        players = new Player[PLAYER_COUNT];
        for (int i = 0; i < players.length; i++) {
            if (testMode = true) {
                players[i] = new Player(names[i]);
                System.out.println(players[i]);
            } else {
                players[i] = new Player(PrimaryController.playerName[i]);
            }

            rolesAssigned = false;
        }
    }

    public void gameLoop(){
        int turnNumber = 0;
        //addPlayersPhase();
        assignRoles();
        tellRoles();
        morningPhase(turnNumber);//Used to tell players their role
        nightPhase();//so we skip the night phase
        do{//Do while loop... haven't seen much of you
            //But I use you because we won't have a winner first turn!

            turnNumber++;
            morningPhase(turnNumber);//Used to tell players their role
            dayPhase();
            if(checkWinner() != null) break;
            nightPhase();

        }while(checkWinner() == null && turnNumber < MAX_TURNS);
        if(turnNumber == MAX_TURNS){
            System.out.println("The train has arrived at its destination.");
        }
        Scanner sc = new Scanner(System.in);
        sc.close();
    }

    private void nightPhase(){
        Player[] selection = new Player[PLAYER_COUNT];
        for(int i = 0; i < players.length; i++){
            if(players[i].isAlive()) selection[i] = players[i].doActivity(players);
        }
        for(int i = 0; i < players.length; i++){
            if(selection[i] != null){
                nightHandler(players[i],selection[i]);
            }
        }
        //Doing this for all players (not just murderer and detective) to future-proof this
        //In the future, other roles will have
    }
    public void nightHandler(Player actor, Player acted){//This is going to have to be replaced when we add more roles
        if(actor instanceof Murderer){
            acted.killedBy(actor);
        }
        if(actor instanceof Detective){
            Player p = acted.getVisited();
            String name;
            if(p == null){
                name = "nobody!";
            }
            else{
                name = p.getName();
            }
            actor.hear(acted.getName() + " visited " + name);
        }
    }
    private void morningPhase(int turn){
        System.out.print(Integer.toString(turn) + "\nGood morning!\nLiving players: ");
        for(int i = 0; i < players.length; i++){
            if(players[i].isAlive()) System.out.print(players[i].name + ", ");
        }
        System.out.println("\n\nPress ENTER to continue...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        for(int i = 0; i < players.length; i++){
            if(players[i].isAlive()) players[i].displayMessages();
        }
    }
    private void dayPhase(){
        for(int i = 0; i < PLAYER_COUNT; i++){
            if(players[i].isAlive()){
                votes[i] = players[i].vote(players);
            }
        }
        Player chosen = tallyVotes();
        if(chosen != null){
            chosen.kill();
        }
    }


    public int countInnocents(){
        int a = 0;
        for(int i = 0; i < PLAYER_COUNT; i++){
            if(players[i] instanceof Innocent && players[i].isAlive()){
                a++;
            }
        }
        return a;
    }
    public int countMurderers(){
        int a = 0;
        for(int i = 0; i < PLAYER_COUNT; i++){
            if(players[i] instanceof Murderer && players[i].isAlive()){
                a++;
            }
        }
        return a;
    }

    public boolean addPlayer(Player player){//Take a wild guess what this does
        for(int i = 0; i < PLAYER_COUNT; i++){//searches for a null spot and then puts the player in that spot
            if(players[i] == null){
                players[i] = player;
                return true;//returns true if the null spot was found
            }
        }
        return false;//returns false if the player was never added
    }

    public static void shuffle(Object[] a){//Simpler for me to make this its own function
        Random r = new Random();
        int val;
        Object tmp;
        for(int i = 0; i < a.length; i++){
            tmp = a[i];
            val = r.nextInt(a.length);
            a[i] = a[val];
            a[val] = tmp;
        }
    }

    private Role[] defaultRoles(){
        return new Role[] {Role.INNOCENT,Role.INNOCENT,Role.INNOCENT,Role.INNOCENT,Role.DETECTIVE,Role.MURDERER};
    }

    public boolean assignRoles(){
        if(!rolesAssigned){
            Role[] roleList = defaultRoles();
            shuffle(roleList);
            for(int i = 0; i < PLAYER_COUNT; i++){
                switch(roleList[i]){
                    case INNOCENT:
                        players[i] = players[i].setInnocent();
                        break;
                    case MURDERER:
                        murderer = players[i].setMurderer();
                        players[i] = murderer;
                        break;
                    case DETECTIVE:
                        detective = players[i].setDetective();
                        players[i] = detective;
                        break;
                }
            }
            rolesAssigned = true;
            return true;
        }
        else{
            return false;
        }
    }
    public Player getPlayer(String playerName){
        for (Player player:
             players) {
            if (player.nameIs(playerName)){
                return player;
            }
        }
        return null;
    }
    public void tellRoles(){
        for(int i = 0; i < PLAYER_COUNT; i++){
            players[i].tellRole();
        }
    }

    public boolean receiveVote(Player voter, Player target){//I didn't end up using this function, but I'm not deleting it
        int targetIndex = 0;
        for(int i = 0; i < players.length; i++){
            if(players[i] == voter && target.isAlive()){//sets votes[i] to target, where i is
                targetIndex = i;
            }
        }
        votes[targetIndex] = target;
        return true;
    }
    public Player tallyVotes(){
        int[] tally = new int[votes.length];//should be initialized to 0
        int workingIndex;
        for(int i = 0; i < votes.length; i++){
            if(votes[i] != null){
                workingIndex = getPlayerIndex(votes[i]);
                tally[workingIndex]++;
            }
        }
        int threshold = getLivingPlayerCount()/2+1;//integer division always rounds down, so x/2+1 will always be
        for(int i = 0; i < players.length; i++){//guaranteed to be the smallest number greater than half
            if(tally[i] >= threshold){//if the player's tally exceeds the threshold, return this player
                clearVotes();//clear the votes after the votes have all been tallied
                System.out.println(players[i].name + " got kicked off the train! Good luck to them!");
                return players[i];
            }
        }
        clearVotes();
        return null;//if no player's tally exceeds the threshold, return null
    }

    public int getPlayerIndex(Player player){//Take a wild guess which this function does :)
        for(int i = 0; i < players.length; i++){
            if(players[i] == player){
                return i;
            }
        }
        return -1;//error case
    }

    public void clearVotes(){
        for(int i = 0; i < votes.length; i++){
            votes[i] = null;
        }
    }

    public int getLivingPlayerCount(){
        int tally = 0;
        for(int i = 0; i < players.length; i++){
            if(players[i].isAlive()){
                tally++;
            }
        }
        return tally;
    }

    public Role checkWinner(){
        int livingInnocents = 0;
        int livingKillers = 0;
        for (Player player:
             players) {
            if (player instanceof Innocent && player.isAlive()){
                livingInnocents++;
            }
            if (player instanceof Murderer && player.isAlive()){
                livingKillers++;
            }

        }
        if (livingInnocents == 0 && livingKillers > 0){
            System.out.println("Murderers won.");
            return Role.MURDERER;
        }
        else if (livingKillers == 0 && livingInnocents > 0){
            System.out.println("Innocents won.");
            return Role.INNOCENT;
        }
        else if(livingKillers == 0 && livingInnocents == 0){
            System.out.println("Nobody won.");
            return Role.NONE;
        }
        return null;
//        Role team = null;
//        //Stores the team of the first living player it finds
//        //into "team". Either this team has already won or
//        //no team has yet.
//        for(int i = 0; i < players.length; i++){
//            if (players[i] == null){
//                System.out.println(i);
//                continue;
//            }
//            if (players[i].isAlive()) {
//                if(players[i] instanceof Innocent){
//                    //This will resolve true for Detectives and any other
//                    //innocent roles we may define in the future,
//                    //since Detective extends Innocent
//                    team = Role.INNOCENT;
//                    break;
//                }
//                else if(players[i] instanceof Murderer){
//                    //Likewise, this will resolve true for any
//                    //future evil roles if we're smart and make them
//                    //extensions of the Murderer type
//                    team = Role.MURDERER;
//                    break;
//                }
//                else{
//                    //Handle neutral roles
//                    //DO NOT BREAK... neutral roles by definition win with innocents and murderers
//                    //Probably won't ever actually need anything here
//                }
//            }
//        }
//        if(team == null){
//            return Role.NONE;//If team is still null, it means everyone is dead. IDK who should win here!
//        }
//        switch(team){
//            case INNOCENT:
//                for(int i = 0; i < players.length; i++){
//                    if(players[i].isAlive() && players[i] instanceof Murderer){
//                        return Role.NONE;//Game has not ended
//                    }
//                }
//                return Role.INNOCENT;//Will only return if no murderer are alive
//            case MURDERER:
//                for(int i = 0; i < players.length; i++){
//                    if(players[i].isAlive() && players[i] instanceof Innocent){
//                        return Role.NONE;//Game has not ended
//                    }
//                }
//                return Role.MURDERER;//Will only return if no innocents are alive
//        }
//        System.out.println("hi");
//        for (Player player:
//             players) {
//            System.out.println(player.name + " " + player.isAlive());
//        }
//        return Role.NONE;//In case something has gone very wrong
    }

}
