package model;

public interface GamePlayer<M extends Move> {

    /**
     * Determines the next Move and returns it to the given handler
     *
     * @param state   GameState to base the Move on
     * @param handler Handles the result of this player's move
     */
    void makeMove(GameState<M> state, MoveHandler<M> handler);

    interface MoveHandler<M extends Move> {
        void handle(M move);
    }
}
