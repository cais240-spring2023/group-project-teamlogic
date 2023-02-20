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
   * Method for a player to vote for another player.
   * @param player
   * @return True if a vote for a player is successful, false otherwise.
   */
  boolean vote(Player player);
}
