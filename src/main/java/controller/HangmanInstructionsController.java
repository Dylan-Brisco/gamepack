package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class HangmanInstructionsController extends Controller {
    @FXML
    private Button back;

    @FXML
    public void onBack() { // if back is clicked return to the Hangman Menu
        mainApp.openView(new FXMLLoader(getClass().getResource("../view/Hangman.fxml")));
    }
}
