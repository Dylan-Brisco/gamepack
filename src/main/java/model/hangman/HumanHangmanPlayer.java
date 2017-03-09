package model.hangman;

import controller.LimitedCharacterField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.GamePlayer;
import model.GameState;

import java.util.stream.Collectors;


public class HumanHangmanPlayer implements GamePlayer<HangmanMove> {

    private Button confirm; //button to confirm a user guess
    private LimitedCharacterField entry; // where the user enters their guess
    private EventHandler<ActionEvent> buttonHandler;

    /**
     * Sets the Textfield and the Button
     * @param confirm the confirm button
     * @param entry the limited textfield
     */
    public void setNodes(Button confirm, LimitedCharacterField entry) {
        this.confirm = confirm;
        this.entry = entry;
    }

    /**
     * Will set the limited text field to the valid characters
     * Will handle button presses
     * @param state   GameState to base the Move on
     * @param handler Handles the result of this player's move
     */
    @Override
    public void makeMove(GameState<HangmanMove> state, MoveHandler<HangmanMove> handler) {
        entry.setCharacters(state.getValidMoves().stream().map(HangmanMove::getCharacter)
                .collect(Collectors.toSet()));
        entry.clear();

        buttonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (entry.getCharacter() != null) { // if there is something in the text field
                    handler.handle(new HangmanMove(entry.getCharacter())); // handles a move
                    confirm.removeEventHandler(ActionEvent.ANY, this); // removes event handler after button is clicked to avoid multiple clicks
                }
            }
        };
        confirm.addEventHandler(ActionEvent.ANY, buttonHandler); // after handle is ran, event handler is re-added to the button
    }
}
