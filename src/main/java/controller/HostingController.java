package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class HostingController extends Controller {

        @FXML
        private Button back;

        @FXML
        private void initialize() {
        }

        @FXML
        private void onBack() throws Exception { //returns to lobby if back is clicked
            mainApp.openView(new FXMLLoader(getClass().getResource("/view/Lobby.fxml")));
        }
}
