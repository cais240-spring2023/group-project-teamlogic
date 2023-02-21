package edu.wsu.model;

public class Investigator implements Player {

    String role;
    String roleType;
    String playerName;
    boolean aliveStatus;
    int votes;

    public Investigator(){
        role = "investigator";
        roleType = "good";
        playerName = "";
        aliveStatus = true;
        votes = 0;
    }
    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getRoleType() {
        return roleType;
    }

    @Override
    public String getName() {
        return playerName;
    }

    @Override
    public boolean isAlive() {
        if (aliveStatus == true)
            return true;
        return false;
    }

    @Override
    public boolean vote(Player player) {
        return false;
    }

    @Override
    public int voteCount() {
        return 0;
    }

    @Override
    public boolean takeTurn() {
        return false;
    }
}
