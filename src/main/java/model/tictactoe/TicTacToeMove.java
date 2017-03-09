package model.tictactoe;

import model.Move;

public class TicTacToeMove implements Move {
    private int row;
    private int col;
    private TicTacToePiece piece;

    /**
     * A TicTacToeMove is represented as a piece going into a specific row and column
     * @param row row location we are adding a piece
     * @param col column location we are adding a piece
     * @param piece the piece we are adding, either X or O
     */
    public TicTacToeMove(int row, int col, TicTacToePiece piece) {
        this.row = row;
        this.col = col;
        this.piece = piece;
    }

    /**
     * Returns a row
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns a column
     * @return column
     */
    public int getCol() {
        return col;
    }

    /**
     * Will return a piece
     * @return either X or O or Neither
     */
    public TicTacToePiece getPiece() {
        return piece;
    }

    @Override
    public String toString() {
        return row + ", " + col + ", " + piece;
    }

    //no setters because we do not need to changed moves, not dynamic
}
