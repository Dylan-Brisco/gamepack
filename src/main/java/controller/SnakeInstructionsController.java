package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Created by dylanbrisco on 1/12/17.
 */
public class SnakeInstructionsController extends Controller {
    @FXML
    private Button back;

    @FXML
    public void onBack() throws Exception { // if back is clicked return to the TicTacToe menu
        mainApp.openView(new FXMLLoader(getClass().getResource("../view/Snake.fxml")));
    }
}