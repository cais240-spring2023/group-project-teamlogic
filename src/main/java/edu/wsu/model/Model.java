package edu.wsu.model;

import edu.wsu.App;
import edu.wsu.view.MessageDisplayerFX;

import java.util.Random;
import java.util.Scanner;


public class Model
{
    /**

    HOW TO ADD A NEW ROLE

     1. Create a new class
     2. Add a night handler override function to this new class
     3. Add the Role to the Role Enum
     4. Add the Role to the Role List, so that it can be assigned
     5. Add the Role to the Assign Roles function in model
     6. Add an assign role function to the Player class
     7. Add to debug mode



    **/

    private Player whoseTurn;//Set to point to a player when waiting to receive votes or night actions, null otherwise

    private static App appLink;

    public Player[] players;
    private static final int PLAYER_COUNT = 12;
    public static int MAX_TURNS = 16;
    public boolean rolesAssigned;//keeps track of whether the roles were already assigned
    //                             because I can't directly access it the way I stored this information

    private Player[] votes = new Player[PLAYER_COUNT];//keeps track of each player's vote
    //index i -> player i's vote
    //e.g. votes[0] = Joebob, player 0 voted for Joebob
    public static final boolean TEXT_MODE = false;
    public static final boolean TEST_MODE = true;
    private Player[] selection = new Player[PLAYER_COUNT];
    private int turnNumber = 0;
    public static Model m = null;




    public enum Role{
        NONE, INNOCENT, MURDERER, DETECTIVE, DOCTOR, ENGINEER;
        public static String[] getStrings(){
            return new String[] {"Innocent","Murderer","Detective","Doctor","Engineer"};
        }
        public static Role[] getAll(){
            return new Role[] {INNOCENT,MURDERER,DETECTIVE,DOCTOR,ENGINEER};
        }
        public static Role get(int i){
            return getAll()[i];
        }
    }
    public static void setAppLink(App app){
        Player.setAppLink(app);
        appLink = app;
    }

    public Model(){
        players = new Player[PLAYER_COUNT];
        rolesAssigned = false;
        m = this;
    }

    public Player whoseTurnIsIt(){
        return whoseTurn;
    }
    public Player[] getPlayers(){
        return players;
    }
    public void incrementTurn(){
        turnNumber++;
    }
    public int getTurn(){
        return turnNumber;
    }
    public String listLivingPlayers(){
        String livingPlayers = new String();
        for(int i = 0; i < players.length; i++){
            if(players[i] != null && players[i].isAlive()) livingPlayers += players[i].getName() + ", ";
        }
        return livingPlayers.substring(0,livingPlayers.length()-2);
    }

    public void gameLoop(){
        //addPlayersPhase();
        assignRoles();
        tellRoles();
        morningPhase(turnNumber);//Used to tell players their role
        nightPhase();//so we skip the night phase
        do{//Do while loop... haven't seen much of you
            //But I use you because we won't have a winner first turn!

            incrementTurn();
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
        for(int i = 0; i < players.length; i++){
            if(players[i].isAlive()){
                whoseTurn = players[i];
                selection[i] = players[i].doActivity(players);
                whoseTurn = null;
            }
        }
        nightOver();
    }
    public void submitAction(Player actor, Player acted){
        if(acted.isAlive()) {
            int i = getPlayerIndex(actor);
            selection[i] = acted;
        }
    }
    public void nightOver(){
        for(int i = 0; i < players.length; i++){
            if(selection[i] != null){
                players[i].nightHandler(selection[i]);
            }
        }
        //Doing this for all players (not just murderer and detective) to future-proof this
        //In the future, other roles will have
    }

    public void onMorning(){
        for(int i = 0; i < players.length; i++){
            if(players[i] != null) players[i].onMorning();
        }
    }

    //NO LONGER USED
    public void nightHandler(Player actor, Player acted){//This is going to have to be replaced when we add more roles
        if(actor instanceof Murderer){
            acted.killedBy(actor);
        }

        //Separate evil and good roles, no else if here

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
        else if(actor instanceof Doctor){
            if(acted.justDied()){
                acted.revive();//They didn't actually die, they got healed by the doctor lol
                acted.hear(Doctor.healString());
            }
            else acted.setImmune();
        }
    }
    private void morningPhase(int turn){
        String goodMorning = "Good morning!\nLiving players: ";
        for(int i = 0; i < players.length; i++){
            if(players[i].isAlive()) goodMorning += players[i].name + ", ";
        }
        goodMorning = goodMorning.substring(0,goodMorning.length()-2);
        if(TEXT_MODE) {
            System.out.println(goodMorning + "\n\n\nPress ENTER to continue...");
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
        }
        else{
            MessageDisplayerFX.display("Day " + turn, goodMorning, appLink, this);
        }
        for(int i = 0; i < players.length; i++){
            if(players[i].isAlive()){
                whoseTurn = players[i];
                players[i].displayMessages(this);
                whoseTurn = null;
            }
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
    //make userNames from the input be put into names for players
    public void addPlayersPhase(String[] playerNames){
        for(int i = 0; i < playerNames.length; i++) {
            if (playerNames[i] != null){
                addPlayer(new Player(playerNames[i]));
            }
        }
    }
    public void printAllPlayerNames(){
        for(int i = 0; i < players.length; i++){
            System.out.println(players[i].getName());
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
    public int countPlayers(){
        int count = 0;
        for(int i = 0; i < PLAYER_COUNT; i++){
            if(players[i] != null) count++;
        }
        return count;
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

    private Role[] defaultRoles(int count){
        Role[] fullList = new Role[] {Role.INNOCENT,Role.DETECTIVE,Role.MURDERER,Role.ENGINEER,Role.DOCTOR,Role.INNOCENT,Role.INNOCENT,Role.MURDERER,Role.INNOCENT,Role.INNOCENT,Role.INNOCENT,Role.MURDERER};
        Role[] shortList = new Role[count];
        for(int i = 0; i < count; i++){
            shortList[i] = fullList[i];
        }
        return shortList;
    }

    public boolean assignRoles(){
        if(!rolesAssigned){
            int count = 0;
            for(int i = 0; i < players.length; i++){
                if(players[i] != null) count++;
            }
            Role[] roleList = defaultRoles(count);
            shuffle(roleList);
            for(int i = 0; i < roleList.length; i++){
                switch(roleList[i]){
                    case INNOCENT:
                        players[i] = players[i].setInnocent();
                        break;
                    case MURDERER:
                        players[i] = players[i].setMurderer();
                        break;
                    case DETECTIVE:
                        players[i] = players[i].setDetective();
                        break;
                    case DOCTOR:
                        players[i] = players[i].setDoctor();
                        break;
                    case ENGINEER:
                        players[i] = players[i].setEngineer();
                        break;
                }
            }
            rolesAssigned = true;
            if(!TEXT_MODE && TEST_MODE){
                for(int i = 0; i < players.length; i++){
                    if(players[i] instanceof Detective) System.out.println(players[i].getName() + " is a detective.");
                    else if(players[i] instanceof Innocent) System.out.println(players[i].getName() + " is an innocent.");
                    else if(players[i] instanceof Murderer) System.out.println(players[i].getName() + " is a murderer.");
                    else if(players[i] != null) System.out.println(players[i].getName() + " has no role!");
                }
            }
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
        for(int i = 0; i < players.length; i++){
            if(players[i] != null) players[i].tellRole();
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
        final int threshold = getLivingPlayerCount()/2+1;//           integer division always rounds down, so x/2+1 will always be
        final String kickedOffText = " got kicked off the train!";//  guaranteed to be the smallest number greater than half
        final String goodLuck = " Good luck to them!";
        for(int i = 0; i < players.length; i++){
            if(tally[i] >= threshold){//if the player's tally exceeds the threshold, return this player
                clearVotes();//clear the votes after the votes have all been tallied
                if(TEXT_MODE) System.out.println(players[i].name + kickedOffText + goodLuck);
                else MessageDisplayerFX.display("Vote result",players[i].name + kickedOffText + goodLuck, appLink, this);
                return players[i];
            }
        }
        clearVotes();
        if(TEXT_MODE) System.out.println("Nobody" + kickedOffText);
        else MessageDisplayerFX.display("Vote result","Nobody" + kickedOffText, appLink, this);
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
    public Player getNextPlayer(Player player){
        if(player == null && players[0].isAlive()) return players[0];
        else if(player == null) return getNextPlayer(players[0]);
        int i = getPlayerIndex(player);
        i++;
        if(i == players.length) return null;
        else if(players[i] == null) return null;
        else if(players[i].isAlive()) return players[i];
        else return getNextPlayer(players[i]);//if the next player is dead, get the next next
    }

    public void clearVotes(){
        for(int i = 0; i < votes.length; i++){
            votes[i] = null;
        }
    }

    public int getLivingPlayerCount(){
        int tally = 0;
        for(int i = 0; i < players.length; i++){
            if(players[i] != null && players[i].isAlive()){
                tally++;
            }
        }
        return tally;
    }

    public void clearPlayers(){//DO NOT USE EVER
        players = new Player[PLAYER_COUNT];
    }

    public Role checkWinner(){
        int livingInnocents = 0;
        int livingKillers = 0;
        for (Player player:
             players) {
            if (player instanceof Innocent && player.isAlive()){
                livingInnocents++;
                System.out.println(player.getName() + " is a living innocent. That makes " + livingInnocents);
            }
            if (player instanceof Murderer && player.isAlive()){
                livingKillers++;
                System.out.println(player.getName() + " is a living killer. That makes " + livingKillers);
            }

        }
        if (livingInnocents == 0 && livingKillers > 0){
            System.out.println("Killers win");
            return Role.MURDERER;
        }
        else if (livingKillers == 0 && livingInnocents > 0){
            System.out.println("Innos win");
            return Role.INNOCENT;
        }
        else if(livingKillers == 0 && livingInnocents == 0){
            System.out.println("This game is empty");
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
