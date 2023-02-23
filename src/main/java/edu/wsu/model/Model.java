package edu.wsu.model;

import java.util.Random;

public class Model
{

    public Player[] players;
    private static final int PLAYER_COUNT = 6;
    private static final int MAX_TURNS = 15;//easy to change
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

    public Model(){
        players = new Player[PLAYER_COUNT];
        rolesAssigned = false;
    }

    public void gameLoop(){
        int turnNumber = 0;
        addPlayersPhase();
        assignRoles();
        tellRoles();
        morningPhase();//Used to tell players their role
        do{//Do while loop... haven't seen much of you
            //But I use you because we won't have a winner first turn!

            nightPhase();
            turnNumber++;
            morningPhase();
            dayPhase();

        }while(checkWinner() == null && turnNumber < MAX_TURNS);
    }

    private void nightPhase(){
        Player selection;
        for(int i = 0; i < players.length; i++){
            selection = players[i].doActivity(players);
            if(selection != null){
                nightHandler(players[i],selection);
            }
        }//Doing this for all players (not just murderer and detective) to future-proof this
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
    private void morningPhase(){
        for(int i = 0; i < players.length; i++){
            players[i].displayMessages();
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

    public void addPlayersPhase(){
        for(int i = 0; i < players.length; i++){
            addPlayer(Player.create());
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
    public void tellRoles(){
        for(int i = 0; i < PLAYER_COUNT; i++){
            players[i].tellRole();
        }
    }

    public boolean receiveVote(Player voter, Player target){//I didn't end up using this function, but I'm not deleting it
        for(int i = 0; i < players.length; i++){
            if(players[i] == voter && voter.isAlive()){//sets votes[i] to target, where i is
                votes[i] = target;//                     the voter's index in the players list
                return true;
            }
        }
        return false;
    }
    public Player tallyVotes(){
        int[] tally = new int[PLAYER_COUNT];//should be initialized to 0
        int workingIndex;
        for(int i = 0; i < players.length; i++){
            if(votes[i] != null){
                workingIndex = getPlayerIndex(votes[i]);
                tally[workingIndex]++;
            }
        }
        int threshold = getLivingPlayerCount()/2+1;//integer division always rounds down, so x/2+1 will always be
        for(int i = 0; i < players.length; i++){//guaranteed to be the smallest number greater than half
            if(tally[i] >= threshold){//if the player's tally exceeds the threshold, return this player
                clearVotes();//clear the votes after the votes have all been tallied
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
        Role team = null;
        //Stores the team of the first living player it finds
        //into "team". Either this team has already won or
        //no team has yet.
        for(int i = 0; i < players.length; i++){
            if (players[i].isAlive()) {
                if(players[i] instanceof Innocent){
                    //This will resolve true for Detectives and any other
                    //innocent roles we may define in the future,
                    //since Detective extends Innocent
                    team = Role.INNOCENT;
                    break;
                }
                else if(players[i] instanceof Murderer){
                    //Likewise, this will resolve true for any
                    //future evil roles if we're smart and make them
                    //extensions of the Murderer type
                    team = Role.MURDERER;
                    break;
                }
                else{
                    //Handle neutral roles
                    //DO NOT BREAK... neutral roles by definition win with innocents and murderers
                    //Probably won't ever actually need anything here
                }
            }
        }
        if(team == null){
            return Role.NONE;//If team is still null, it means everyone is dead. IDK who should win here!
        }
        switch(team){
            case INNOCENT:
                for(int i = 0; i < players.length; i++){
                    if(players[i].isAlive() && players[i] instanceof Murderer){
                        return null;//Game has not ended
                    }
                }
                return Role.INNOCENT;//Will only return if no murderer are alive
            case MURDERER:
                for(int i = 0; i < players.length; i++){
                    if(players[i].isAlive() && players[i] instanceof Innocent){
                        return null;//Game has not ended
                    }
                }
                return Role.MURDERER;//Will only return if no innocents are alive
        }
        return null;//In case something has gone very wrong
    }

}
