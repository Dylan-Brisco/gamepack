package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;


public class MainMenuController extends Controller {

    // list of all games
    private static final String[] GAMES = {"Battleship", "Hangman", "Pong", "Snake", "Simon", "Tic-tac-toe"};

    // list of all views
    private static final String[] VIEWS = {"Battleship.fxml", "Hangman.fxml", "Pong.fxml", "Snake.fxml", "Simon.fxml", "TicTacToe.fxml"};


    @FXML
    private ListView<String> gameList;


    @FXML
    private void initialize() {
        ObservableList<String> games = FXCollections.observableArrayList(GAMES);
        gameList.setItems(games); // creates a list of games


        gameList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + VIEWS[newValue.intValue()]));
            // assigns views to the list elements
            mainApp.openView(loader);
        });
    }
}