package model.tictactoe;

import model.GameState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class TicTacToeBoard extends GameState<TicTacToeMove> {

    TicTacToePiece[][] board;

    public TicTacToeBoard() {
        turn = 0;
        board = new TicTacToePiece[3][3]; // tic tac toe board can be represented as a 3 by 3 2D array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = TicTacToePiece.NO_PIECE; // populates all locations in 2D array with No Piece (blank board)
            }
        }
    }

    /**
     * Returns a TicTacToePiece object that represents the winner of the
     * game, or NO_PIECE if no one has won yet or there is a tie
     *
     * @return the TicTacToePiece objects that represents the winner of the game,
     * or NO_PIECE if there is no winner yet or there is a tie
     */
    private TicTacToePiece getWinner() {

        // checks if whole row is occupied by same piece
        for (int i = 0; i < 2; i++) {
            if (board[i][0] != TicTacToePiece.NO_PIECE && board[i][0] == board[i][1]
                    && board[i][1] == board[i][2]) {
                return board[i][0];
            }
        }

        // checks if whole column is occupied by same piece
        for (int i = 0; i < 2; i++) {
            if (board[0][i] != TicTacToePiece.NO_PIECE && board[0][i] == board[1][i]
                    && board[1][i] == board[2][i]) {
                return board[0][i];
            }
        }

        // checks diag 1
        if (board[0][0] != TicTacToePiece.NO_PIECE && board[0][0] == board[1][1] &&
                board[1][1] == board[2][2]) {
            return board[0][0];
        }

        // checks diag 2
        if (board[2][0] != TicTacToePiece.NO_PIECE && board[2][0] == board[1][1] &&
                board[1][1] == board[0][2]) {
            return board[2][0];
        }

        // no one has won yet or tie
        return TicTacToePiece.NO_PIECE;
    }

    @Override
    public boolean gameOver() {
        TicTacToePiece winner = getWinner();
        boolean isWinner = winner != TicTacToePiece.NO_PIECE; // make sure winner is an X or O
        boolean isFull = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(board[i][j] == TicTacToePiece.NO_PIECE) {
                    isFull = false; // if one location is populated with nothing, then the board is not full
                }
            }
        }
        return !isWinner && isFull || isWinner;
        // if no winner and board is full then it is a tie and game is over
        // if there is a winner then there is no tie and the game is over
    }


    @Override
    public Map<Integer, Integer> places() {
        Map<Integer, Integer> m = new HashMap<>();
        if(gameOver()) {
            TicTacToePiece winner = getWinner();
            if (winner == TicTacToePiece.NO_PIECE) { //tie
                m.put(0, 1);
                m.put(1, 1);
            } else {
                m.put(winner.ordinal(), 1);
                m.put((winner.ordinal() + 1) % 2, 2);
                //getWinner().ordinal()+1)%2 is always opposite of getWinner.ordinal()
            }
        }
        return m;
    }

    @Override
    public void makeMove(TicTacToeMove m) throws IllegalArgumentException, NullPointerException {

        if (m == null) {
            throw new IllegalArgumentException("Move cannot be null");
        }

        TicTacToePiece p = m.getPiece();
        int row = m.getRow();
        int col = m.getCol();

       // System.out.println("Make Move is " + m);
        if (p == TicTacToePiece.NO_PIECE) {
            throw new IllegalArgumentException("Cannot Add A No TicTacToePiece");
        }
        if (p == null) {
            throw new IllegalArgumentException("Cannot Be Null");
        }
        if (row > 2) {
            throw new IllegalArgumentException("Rows Cannot Be > 2");
        }
        if (col > 2) {
            throw new IllegalArgumentException("Cols Cannot Be > 2");
        }
        if (row < 0) {
            throw new IllegalArgumentException("Rows Cannot Be < 0");
        }
        if (col < 0) {
            throw new IllegalArgumentException("Cols Cannot Be < 0");
        }
        if (board[row][col] != TicTacToePiece.NO_PIECE) {
            throw new IllegalArgumentException("Space Is Already Occupied");
        }
        // all of these if statements are for exception handling.
        // if valid add piece
        board[row][col] = p;

        turn ^= 1;
        // changes turns
        //0 -> 1
        //1 -> 0
    }

    @Override
    /**
     * Returns all of the valid moves we have
     * Valid move is a location in the 2D array not populated
     */
    public Set<TicTacToeMove> getValidMoves() {
        Set<TicTacToeMove> s = new HashSet<>();
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                if(board[i][j] == TicTacToePiece.NO_PIECE) {
                    s.add(new TicTacToeMove(i, j, TicTacToePiece.values()[turn]));
                    // i = row
                    // j = col
                    // TicTacToePiece.values()[turn] will return the piece with the ordinal of the turn
                }
            }
        }
        //System.out.println("Size is of grid is  " + s.size());
        return s;
    }
}