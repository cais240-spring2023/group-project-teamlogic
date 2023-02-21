package edu.wsu.model;

public class Innocent implements Player {

  String role;
  String roleType;
  String playerName;
  boolean aliveStatus;
  int votes;

  public Innocent(){
     role = "innocent";
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
    if (player.isAlive()) {
      //increase a player's number of votes by 1
      return true;
    }
    return false;
  }

  @Override
  public int voteCount() {
    return votes;
  }

  @Override
  public boolean takeTurn() {
    return false;
  }
}
