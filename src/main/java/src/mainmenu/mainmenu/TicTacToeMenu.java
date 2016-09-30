package src.mainmenu.mainmenu;

/**
 * Created by dylanbrisco on 9/22/16.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

    public class TicTacToeMenu extends Application {
        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Tic Tac Toe");
            Button startSinglePlayer = new Button();
            Button startTwoPlayers = new Button();
            Button instructions = new Button();
            startSinglePlayer.setText("Single Player");
            startTwoPlayers.setText("Two Players");
            instructions.setText("Instructions");

            startSinglePlayer.setOnAction(event -> {
                //TODO implement single player
            });

            startTwoPlayers.setOnAction(event -> {
                // TODO implement two player
            });

            instructions.setOnAction(event -> {
                // TODO implement instructions screen
            });


            StackPane root = new StackPane();
            root.getChildren().add(startSinglePlayer);
            root.getChildren().add(startTwoPlayers);
            root.getChildren().add(instructions);
            primaryStage.setScene(new Scene(root, 300, 250));
            primaryStage.show();
        }
    }

