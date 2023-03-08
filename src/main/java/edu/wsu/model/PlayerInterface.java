package edu.wsu.model;

public interface PlayerInterface
{
 /**
  * Tells a player what role they are assigned at the start of a game.
  */
 public void tellRole();

 /**
  * Lets a dead player return to the game (changes their status from dead to alive).
  */
 public void revive();

 /**
  * Changes a player's status to dead (out of the game).
  * (Used when a player is out of the game without being murdered ex. voted out)
  */
 public void kill();

 /**
  * Changes a player's status to dead and lets that player know who killed them.
  * (Used when a player is murdered)
  * @param murderer
  */
 public void killedBy(Player murderer);

 /**
  * Checks to see if the player is alive or dead.
  * @return True is the player is alive, false otherwise.
  */
 public boolean isAlive();

 /**
  * Attempts to submit a vote for an alive player. Will only be successful if the selected player is alive.
  * @param players
  * @return The selected player.
  */
 public Player vote(Player[] players);

 /**
  *
  * @param players
  * @return
  */
 public Player doActivity(Player[] players);

 /**
  *
  * @param players
  * @return
  */
 public Player activityHandler(Player[] players);

 /**
  *
  * @param name
  * @return
  */
 public boolean nameIs(String name);

 /**
  * Gets the name of the player.
  * @return The name of the player.
  */
 public String getName();

 /**
  * Checks if a player visited another player.
  * @return A player that visited you during the night if anyone did.
  */
 public Player getVisited();
 public void hear(String message);
 public void clearMessages();
 public boolean displayMessages();

 /**
  * Creates a new innocent player.
  * @return The new player assigned with the innocent role.
  */
 public Innocent setInnocent();

 /**
  * Creates a new murderer.
  * @return The new player assigned with the murderer role.
  */
 public Murderer setMurderer();

 /**
  * Creates a new detective.
  * @return The new player assigned with the detective role.
  */
 public Detective setDetective();
 public void disableInput();
}
