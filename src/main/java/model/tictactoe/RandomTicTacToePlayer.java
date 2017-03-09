package model.tictactoe;

import model.GamePlayer;
import model.GameState;

import java.util.Collection;

public class RandomTicTacToePlayer implements GamePlayer<TicTacToeMove> {

    /**
     * @param state the current state of the board, spots occupied, etc.
     * @param handler
     */
    @Override
    public void makeMove(GameState<TicTacToeMove> state, MoveHandler<TicTacToeMove> handler) {
        Collection<TicTacToeMove> moves = state.getValidMoves();
        int i = (int)(Math.random() * (moves.size() + 1));
        int j = 0;
        for (TicTacToeMove m : moves) {
            if (i == j) {
                handler.handle(m);
                return;
            }
            j++;
        }
        handler.handle(null);
    }
}