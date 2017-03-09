package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Created by dylanbrisco on 3/8/17.
 */
public class BattleshipInstructionsController extends Controller{
    @FXML
    private Button back;

    @FXML
    public void onBack() {
        mainApp.openView(new FXMLLoader(getClass().getResource("../view/Battleship.fxml")));
    }
}
