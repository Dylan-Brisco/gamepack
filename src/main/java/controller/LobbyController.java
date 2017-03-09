package controller;

import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.util.regex.Pattern;

public class LobbyController extends Controller {

    private String hostIP;

    @FXML
    private Button connecting;

    @FXML
    private Button hosting;

    @FXML
    private Button back;

    @FXML
    private void initialize() {

    }

    @FXML
    public void onBack() {
        mainApp.openView(new FXMLLoader(getClass().getResource("/view/MainMenu.fxml")));
    }

    @FXML
    public void handleHosting() {
        mainApp.openView(new FXMLLoader(getClass().getResource("/view/HostingMenu.fxml")));
    }

    @FXML
    public void handleConnecting() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Connect to a host");
        dialog.setHeaderText("Connect to a host");
        dialog.setContentText("Enter host IP address:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> {
            if (isValidIP(s)) {
                hostIP = s;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid IP");
                alert.setHeaderText("Invalid IP address");
                alert.showAndWait();
                handleConnecting();
            }
        });
    }


    private boolean isValidIP(String s) {
        return Pattern.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$", s);
    }
}