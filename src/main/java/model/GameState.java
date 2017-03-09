package model;

import java.util.Map;
import java.util.Set;

public abstract class GameState<M extends Move> {

    protected int turn;

    /**
     * Returns true if the game represented by this GameState has finished
     *
     * @return true if the game represented by this GameState has finished
     */
    abstract public boolean gameOver();

    /**
     * Returns a map from the int representing each player to their placement,
     * or an empty map if the game is not finished
     *
     * @return a map from the int representing each player to their placement,
     * or an empty map if the game is not finished
     */
    abstract public Map<Integer, Integer> places();

    /**
     * Applies the given move to this GameState
     *
     * @param m move to apply to this GameState
     * @throws IllegalArgumentException if the given move is not in getValidMoves
     */
    abstract public void makeMove(M m) throws IllegalArgumentException;

    /**
     * Returns a set of valid moves for the current player on the current GameState
     * @return a set of valid moves for the current player on the current GameState
     */
    abstract public Set<M> getValidMoves();

    /**
     * Returns an int representing the current player, or -1 if the game is over
     * @return an int representing the current player, or -1 if the game is over
     */
    public int getCurrentTurn() {
        return turn;
    }

}
