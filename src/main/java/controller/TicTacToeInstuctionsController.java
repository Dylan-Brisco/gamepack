package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

public class TicTacToeInstuctionsController extends Controller {
    @FXML
    private Button back;

    @FXML
    public void onBack() throws Exception { // if back is clicked return to the TicTacToe menu
        mainApp.openView(new FXMLLoader(getClass().getResource("/view/TicTacToe.fxml")));
    }
}
