package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import model.GamePlayer;
import model.hangman.HangmanMove;
import model.hangman.HumanHangmanPlayer;
import model.hangman.RandomHangmanPlayer;

public class HangmanController extends Controller {

    @FXML
    private Button singlePlayer;

    @FXML
    private Button twoPlayers;

    @FXML
    private Button instructions;

    @FXML
    private Button back;

    @FXML
    private void initialize() {

    }

    /**
     * Launches single player
     * Opens single player screen
     * Sets players to a random player and a human player
     */
    @FXML
    public void handleSinglePlayer() throws Exception {
        HangmanGameController c = (HangmanGameController) mainApp.openView(
                new FXMLLoader(getClass().getResource("../view/HangmanGame.fxml")));
        c.setPlayers(new HumanHangmanPlayer());
    }

    @FXML
    public void handleTwoPlayers() throws Exception {
        mainApp.openView(new FXMLLoader(getClass().getResource("../view/Lobby.fxml")));
    }

    /**
     * Opens an instruction screen
     */
    @FXML
    public void handleInstructions() {
        mainApp.openView(new FXMLLoader(getClass().getResource("../view/HangmanInstructions.fxml")));

    }

    /**
     * If back button is clicked return to main menu
     * @throws Exception
     */
    @FXML
    public void onBack() throws Exception {
        mainApp.openView(new FXMLLoader(getClass().getResource("../view/MainMenu.fxml")));
    }
}