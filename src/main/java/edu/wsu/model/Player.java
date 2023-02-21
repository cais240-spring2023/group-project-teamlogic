package edu.wsu.model;

public interface Player {

  /**
   * Get the role of the player.
   * @return The role of the Player
   */
  String getRole();

  /**
   * Get the type of role of the player (good, bad, or neutral).
   * @return The type of role the player has.
   */
  String getRoleType();

  /**
   * Get the name of the player.
   * @return The name of the player.
   */
  String getName();

  /**
   * Checks to see if the player is still alive.
   * @return True if the player is alive, false otherwise.
   */
  boolean isAlive();

  /**
   * Method for a player to vote for another player.
   * @param player
   * @return True if a vote for a player is successful, false otherwise.
   */
  boolean vote(Player player);

  /**
   * Keeps track of the number of votes a player receives.
   * @return The number of votes a player currently has.
   */
  int voteCount();

  /**
   * Allows a player to take their turn, their actions will change depending on the time of day.
   * @return True if their turn was successfully taken, false otherwise.
   */
  boolean takeTurn();
}
