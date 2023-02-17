package edu.wsu.model;

import java.util.Random;

public class Model
{

    public Player[] players;
    private static final int PLAYER_COUNT = 6;
    public Murderer murderer;//pointer to one of the players
    public Detective detective;//pointer also
    public boolean rolesAssigned;//keeps track of whether the roles were already assigned
    //                             because I can't directly access it the way I stored this information



    public enum Role{
        INNOCENT, MURDERER, DETECTIVE;
        public static Innocent setInnocent(Player player){
            return (Innocent) player;//casts the Player object to an Innocent object
        }
        public static Murderer setMurderer(Player player){
            return (Murderer) player;//casts the Player object to a Murderer object
        }
        public static Detective setDetective(Player player){
            return (Detective) player;//casts the Player object to a Detective object
        }
    }

    public Model(){
        players = new Player[PLAYER_COUNT];
        rolesAssigned = false;
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

    public boolean assignRoles(){
        if(!rolesAssigned){
            Role[] roleList = new Role[PLAYER_COUNT];
            shuffle(roleList);
            for(int i = 0; i < PLAYER_COUNT; i++){
                switch(roleList[i]){
                    case INNOCENT:
                        players[i] = Role.setInnocent(players[i]);
                        break;
                    case MURDERER:
                        murderer = Role.setMurderer(players[i]);
                        players[i] = murderer;
                        break;
                    case DETECTIVE:
                        detective = Role.setDetective(players[i]);
                        players[i] = detective;
                        break;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

}
