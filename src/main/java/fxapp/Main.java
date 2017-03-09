package fxapp;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage currentStage;
    private Controller currentController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // loads the main menu
        currentStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/MainMenu.fxml")); // .. references above directory, /view/MainMenu tells us where the view is
        // loader is used to create the view and link elements with the controller
        Parent root = loader.load();
        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setMainApp(this);
        currentController = mainMenuController;
        primaryStage.setTitle("Retro Game Package"); // title of the menu
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false); //user cannot resize the main menu
        primaryStage.show(); // shows the main menu

    }

    public Controller openView(FXMLLoader loader) {
        try {
            Parent root = loader.load(); // assigns a view
            Controller controller = loader.getController();
            controller.setMainApp(this); // sets the screen
            Stage stage = new Stage();
            stage.setTitle("Retro Game Package");
            stage.setScene(new Scene(root));
            currentStage.close(); // closes the previous screen
            currentStage = stage; // sets stage
            currentController = controller; // assigns currentController to the current controller
            stage.setResizable(false); //user cannot resize during a game
            stage.show(); // makes current game visible
            return controller;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Controller getCurrentController() {
        return currentController; // returns the currentController
    }

    public Stage getCurrentStage() {
        return currentStage;
    }
}