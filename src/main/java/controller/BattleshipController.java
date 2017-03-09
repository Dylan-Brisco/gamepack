package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Created by dylanbrisco on 2/27/17.
 */
public class BattleshipController extends Controller {

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
     */
    @FXML
    public void handleSinglePlayer() throws Exception {
        mainApp.openView(new FXMLLoader(getClass().getResource("../view/BattleshipGame.fxml")));
    }

    /**
     * Opens an instruction screen
     */
    @FXML
    public void handleInstructions() {
        mainApp.openView(new FXMLLoader(getClass().getResource("../view/BattleshipInstructions.fxml")));
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