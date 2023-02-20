package edu.wsu.model;

public class Engineer implements Player {

    String role;
    String roleType;
    String playerName;

    public Engineer(){
        role = "engineer";
        roleType = "good";
        playerName = "";
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
    public boolean vote(Player player) {
        return false;
    }
}
