package edu.wsu.model;

public interface ModelInterface {
    /**
     * Contains the order of actions that will occur in game.
     * The game will begin by adding all necessary players and assigning them roles at random.
     * The payers will be told their roles individually then the turn cycles will commence.
     * The first day begins with the night phase -> morning phase -> day phase... repeats until there is a winner
     */
    public void gameLoop();

    /**
     * This phase allows any roles who are allowed to take actions at night to do so.
     * All other players will not have actions to take during this time.
     */
    public void nightPhase();

    /**
     * This handles the actions taken by the roles who are able to do so at this time.
     * @param actor - the player taking an action on another player.
     * @param acted - the player who is having an action taken on them.
     */
    public void nightHandler(Player actor, Player acted);

    /**
     * This phase communicates to players what happened during the night phase.
     * EX. "Billy was murdered"
     */
    public void morningPhase();

    /**
     * This phase allows players to discuss the events of the night/morning phases and gives players
     * an option to vote for 1 other player. They will also be given an option to skip voting if they
     * wish to opt out for the turn.
     */
    public void dayPhase();

    /**
     * This is a one-time phase at the start of the game to allow players to join the game.
     */
    public void addPlayersPhase();

    /**
     * Counts the remaining number of innocent players currently alive in the game.
     * @return The number of alive innocent players.
     */
    public int countInnocents();

    /**
     * Counts the remaining number of murderers currently alive in the game.
     * @return The number of alive murderers.
     */
    public int countMurderers();

}
