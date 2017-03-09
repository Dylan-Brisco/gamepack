package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.GamePlayer;
import model.tictactoe.TicTacToeBoard;
import model.tictactoe.TicTacToeMove;

import java.io.InterruptedIOException;
import java.util.Optional;
import java.util.function.Consumer;


public class TicTacToeGameController extends Controller {

    private GamePlayer<TicTacToeMove> player0; // this player
    private GamePlayer<TicTacToeMove> player1; // opponent
    private TicTacToeBoard board;
    private int thisPlayer;

    @FXML
    private Label turnLabel;

    @FXML
    private GridPane grid;

    @FXML
    private void initialize() {
        board = new TicTacToeBoard(); // Create a new board
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Pane p = new Pane(); // fills a 3X3 grid with clickable panes
               // p.setBackground(new Background(new BackgroundFill(Color.CYAN, CornerRadii.EMPTY, Insets.EMPTY)));
                grid.add(p, row, col);
            }
        }
    }

    /**
     * Sets the players of this game, player1 is the player playing on this controller object
     *
     * @param player0 this player
     * @param player1 opposing player
     */
    public void setPlayers(GamePlayer<TicTacToeMove> player0, GamePlayer<TicTacToeMove> player1) {
        this.player0 = player0;
        this.player1 = player1;
        thisPlayer = 0;
    }

    public void start() {
        doTurn();
    }

    /**
     * Does a turn until the game is over
     * Will make a move with the board and a certain move
     * Updates moves adding a piece and changing who's turn it is
     * @throws IllegalArgumentException if someone chooses a spot that is occupied or space outside of grid
     */

    private void doTurn() throws IllegalArgumentException {
        System.out.println("doTurn");
        GamePlayer<TicTacToeMove> currentTurn = board.getCurrentTurn() == 0 ? player0 : player1;
        currentTurn.makeMove(board, (TicTacToeMove move) -> {
            System.out.println("Move is " + move);
            if(move!=null) {
                board.makeMove(move);
                updateView(move);
                if (board.gameOver()) {
                    gameOver();
                } else {
                    doTurn();
                }
            }
            else {
                doTurn();
            }
        });
    }

    /**
     * Changes the label stating your turn or opponents turn and the current piece
     *
     * @param m The current move
     */
    private void updateView(TicTacToeMove m) {
        System.out.println("updateView");
        Label piece = new Label(m.getPiece().name()); // changes the piece to the current one playing (X->O, O->X)
        piece.setFont(Font.font(30)); // set font size to 30 pt
        piece.setAlignment(Pos.CENTER); // center piece
        grid.setHalignment(piece, HPos.CENTER); // center piece horizontally
        grid.setValignment(piece, VPos.CENTER); // centers piece vertically
        grid.getChildren().removeAll(getGridChild(grid, m.getRow(), m.getCol())); // removes the pane
        grid.add(piece, m.getCol(), m.getRow()); // adds the piece
        turnLabel.setText(board.getCurrentTurn() != thisPlayer ? "Your turn" : "Opponent's turn"); // changes the label to the opposite
    }

    /**
     * Returns an alert saying game over
     */
    private void gameOver() {
        Optional<ButtonType> o = new Alert(Alert.AlertType.CONFIRMATION, "Game over").showAndWait();
        if (!o.isPresent()) {
            mainApp.openView(new FXMLLoader(getClass().getResource("../view/TicTacToe.fxml")));
        }
        o.ifPresent(new Consumer<ButtonType>() {
            @Override
            public void accept(ButtonType buttonType) {
                if (buttonType.equals(ButtonType.OK)) {
                    mainApp.openView(new FXMLLoader(getClass().getResource("../view/TicTacToe.fxml")));
                } else if (buttonType.equals(ButtonType.CANCEL)) {
                    mainApp.openView(new FXMLLoader(getClass().getResource("../view/MainMenu.fxml")));
                }
            }
        });
    }


    /**
     * Returns the current grid
     *
     * @return current grid
     */
    public GridPane getGrid() {
        return grid;
    }

    /**
     * Returns the Pane that was clicked
     * @param pane the grid
     * @param row  the row of the pane that was clicked
     * @param col  the column of the pane that was clicked
     * @return the clicked pane (pane matching row and column)
     */
    private Node getGridChild(GridPane pane, int row, int col) {
        for(Node node : pane.getChildren()) {
            if(node instanceof Pane && GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}