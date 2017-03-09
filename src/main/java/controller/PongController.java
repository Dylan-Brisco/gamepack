package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;


public class PongController extends Controller {

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


      /** Launches two player
       * Players can use arrow keys or the 'W' ans 'S' keys
       * @throws Exception
       */
       @FXML
        public void handleTwoPlayers() throws Exception {
            mainApp.openView(new FXMLLoader(getClass().getResource("../view/PongGame.fxml")));
        }

        /**
         * Opens an instruction screen
         */
        @FXML
        public void handleInstructions() {
            mainApp.openView(new FXMLLoader(getClass().getResource("../view/PongInstructions.fxml")));

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