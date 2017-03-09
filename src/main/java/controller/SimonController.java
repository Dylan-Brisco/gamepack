package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class SimonController extends Controller {

    @FXML
    private Button singlePlayer;

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
        SimonGameController c = (SimonGameController) mainApp.openView(
                new FXMLLoader(getClass().getResource("/view/SimonGame.fxml")));
    }

    /**
     * Opens an instruction screen
     */
    @FXML
    public void handleInstructions() {
        mainApp.openView(new FXMLLoader(getClass().getResource("/view/SimonInstructions.fxml")));
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