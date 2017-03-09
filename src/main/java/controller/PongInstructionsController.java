package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Created by dylanbrisco on 2/9/17.
 */
public class PongInstructionsController extends Controller {
    @FXML
    private Button back;

    @FXML
    public void onBack() {
        mainApp.openView(new FXMLLoader(getClass().getResource("../view/Pong.fxml")));
    }
}
