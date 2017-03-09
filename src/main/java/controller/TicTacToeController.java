package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import model.tictactoe.HumanTicTacToePlayer;
import model.tictactoe.RandomTicTacToePlayer;

public class TicTacToeController extends Controller {

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
        TicTacToeGameController c = (TicTacToeGameController) mainApp.openView(new FXMLLoader(getClass().getResource("/view/TicTacToeGame.fxml")));
        HumanTicTacToePlayer human = new HumanTicTacToePlayer(c.getGrid());
        c.setPlayers(new RandomTicTacToePlayer(), human);
        c.start();
    }

    //Todo Implement Two Player
    @FXML
    public void handleTwoPlayers() throws Exception {
        mainApp.openView(new FXMLLoader(getClass().getResource("/view/Lobby.fxml")));
    }

    /**
     * Opens an instruction screen
     */
    @FXML
    public void handleInstructions() {
        mainApp.openView(new FXMLLoader(getClass().getResource("/view/TicTacToeInstructions.fxml")));

    }

    /**
     * If back button is clicked return to main menu
     * @throws Exception
     */
    @FXML
    public void onBack() throws Exception {
        mainApp.openView(new FXMLLoader(getClass().getResource("/view/MainMenu.fxml")));
    }
}