package model.tictactoe;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import model.GamePlayer;
import model.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HumanTicTacToePlayer implements GamePlayer<TicTacToeMove> {

    private GridPane board;

    public HumanTicTacToePlayer(GridPane board) {
        this.board = board;
    }

    @Override
    public void makeMove(GameState<TicTacToeMove> state, MoveHandler<TicTacToeMove> handler) {
        List<Pair<Node, EventHandler<MouseEvent>>> list = new ArrayList<>();
        for (TicTacToeMove m : state.getValidMoves()) {
            EventHandler<MouseEvent> h = produceHandler(handler, m, list); // waits for the user to click
            Node n = getGridChild(board, m.getRow(), m.getCol()); // makes node the grid element at position row, col
            n.addEventHandler(MouseEvent.MOUSE_CLICKED, h); // adds handler for event
            list.add(new Pair<>(n, h));
        }
    }

    /**
     * Returns an EventHandler that removes all other board EventHandlers and returns
     * the selected Move to the MoveHandler
     *
     * @param handler MoveHandler to call back
     * @param m       Move to give to the handler
     * @param l       List of other EventHandlers that need to be removed
     * @return
     */
    private EventHandler<MouseEvent> produceHandler(MoveHandler<TicTacToeMove> handler, TicTacToeMove m,
                                                    List<Pair<Node, EventHandler<MouseEvent>>> l) {
        return (MouseEvent e) -> {
            for (Pair<Node, EventHandler<MouseEvent>> p : l) {
                p.getKey().removeEventHandler(MouseEvent.MOUSE_CLICKED, p.getValue()); //removes eventHandler from pane
                handler.handle(m); // handles move
            }

        };
    }

    /**
     * Returns the child Node of a GridPane located at the given row and column index
     *
     * @param pane GridPane to return the child of
     * @param row  Row index of the child to return
     * @param col  Column index of the child to return
     * @return The child Node of the given GridPane located at the given row and column index
     */
    private Node getGridChild(GridPane pane, int row, int col) {
        for(Node node : pane.getChildren()) {
            if(node instanceof Pane && GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node; //returns the clicked pane
            }
        }
        return null;
    }
}