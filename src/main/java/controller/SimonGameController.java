package controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import model.simon.SimonBoard;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by dylanbrisco on 1/19/17.
 */
public class SimonGameController extends Controller {

    SimonBoard board = new SimonBoard();

    private boolean currentlyFlashing = false;

    @FXML
    private Button greenButton; // represents the green button on the SimonBoard

    @FXML
    private Button redButton; // represents the red button on the SimonBoard

    @FXML
    private Button yellowButton; // represents the yellow button on the SimonBoard

    @FXML
    private Button blueButton; // represents the blue button on the SimonBoard

    @FXML
    public void greenPressed() { // called when greenButton is pressed
        flashButton(greenButton, Color.GREEN,  Color.rgb(160, 255, 160)); // will flash the button a different shade of green
        makeMove(SimonBoard.Color.GREEN); // will call makeMove with green color
    }

    @FXML
    public void redPressed() { // called when redButton is pressed
        flashButton(redButton, Color.RED,  Color.rgb(255, 160, 160)); // will flash the button a different shade of red
        makeMove(SimonBoard.Color.RED); // will call makeMove with red color
    }

    @FXML
    public void yellowPressed() { // called when yellowButton is pressed
        flashButton(yellowButton, Color.YELLOW, Color.rgb(255, 255, 160)); // will flash the button with a different shade of yellow
        makeMove(SimonBoard.Color.YELLOW); // will call makeMove with yellow color
    }

    @FXML
    public void bluePressed() { // called when blueButton is pressed
        flashButton(blueButton, Color.BLUE,  Color.rgb(160, 160, 255)); // will flash the button with a different shade of blue
        makeMove(SimonBoard.Color.BLUE); // will call makeMove with blue color
    }

    @FXML
    /**
     * Sets up the scene with all of the buttons with their respective colors
     */
    public void initialize() {
        greenButton.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null))); //greenButton green background
        redButton.setBackground(new Background(new BackgroundFill(Color.RED, null, null))); // redButton red background
        yellowButton.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null))); // yellowButton yellow background
        blueButton.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null))); // blueButton blue background

        flashColors(0);
    }

    /**
     * If the board is not currently flashing and the game is not over
     * will call makeMove in the board class with the param color
     * @param color the color that user chooses
     */
    private void makeMove(SimonBoard.Color color) {
        if (!currentlyFlashing && !board.isGameOver()) {
            System.out.println("makeMove");
            board.makeMove(color);
            System.out.println(color.toString());
            if (board.isGameOver()) { // if the game is over, end the game
                System.out.println("gameOver");
                gameOver();
            } else {
                if (board.isSequenceOver()) {// if the sequence is over start flashing the next sequence
                    new AnimationTimer() {
                        long startTime;

                        public void myStart() {
                            startTime = System.nanoTime();
                            start();
                        }


                        @Override
                        public void handle(long now) {
                            if (now - startTime >= (long) 2e9) { // 2 seconds gap

                                System.out.println("animating");
                                stop();
                            }
                        }
                    }.myStart();
                    currentlyFlashing = true;
                    flashColors(0);
                }
            }
        }
    }

    /**
     * Will flash the button that is given a certain color
     * @param button the button that we need to flash
     * @param original the original color of the button i.e green
     * @param newColor the color the button will be temporarily flashed
     */
    private void flashButton(Button button, Color original, Color newColor) {
        if (!currentlyFlashing) { // if we are not currently flashing a color
            button.setBackground(new Background(new BackgroundFill(newColor, null, null))); //set button to new color
            new AnimationTimer() {
                long startTime;

                public void myStart() {
                    startTime = System.nanoTime(); // current time thread has been running in nanoseconds
                    start();
                }


                @Override
                public void handle(long now) {
                    if (now - startTime >= (long) 1e9) { // will flash button for .5 seconds

                        System.out.println("animating");
                        button.setBackground(new Background(new BackgroundFill(original, null, null))); // will reset button to original color
                        stop();
                    }
                }
            }.myStart(); // starts the animation

        }
    }

    /**
     * Will call flashButtons to flash the current sequence
     * @param i the length of the sequence
     */
    private void flashColors(int i) {
        new AnimationTimer() {
            long startTime;

            public void myStart() {
                startTime = System.nanoTime(); // current time thread has been running in nanoseconds
                start();
            }

            @Override
            public void handle(long now) {
                if (now - startTime >= (long) 1e9) { // will flash button for .5 seconds
                    stop();
                }
            }
        }.myStart(); // starts the animation

        if (i < board.getCurrentSequence().size()) {
            currentlyFlashing = true;
            System.out.println("flashColors");
            switch (board.getCurrentSequence().get(i)) { // gets the color we need to flash
                case GREEN: // will flash green
                    flashButtons(greenButton, Color.GREEN, Color.rgb(160, 255, 160), i);
                    break;
                case RED: // will flash red
                    flashButtons(redButton, Color.RED, Color.rgb(255, 160, 160), i);
                    break;
                case YELLOW: // will flash yellow
                    flashButtons(yellowButton, Color.YELLOW, Color.rgb(255, 255, 160), i);
                    break;
                case BLUE: // will flash blue
                    flashButtons(blueButton, Color.BLUE, Color.rgb(160, 160, 255), i);
                    break;
            }
        } else { // means the sequence has been flashed
            currentlyFlashing = false;
        }
    }

    /**
     * Will reset all the buttons back to their original colors
     */
    private void unFlash() {
        greenButton.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        redButton.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        yellowButton.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
        blueButton.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
    }

    /**
     * Will flash the sequence, called by flashColors
     * @param button the button to be flashed
     * @param original the original color of the button
     * @param newColor the color we want to flash the button
     * @param i index of the current sequence
     */
    private void flashButtons(Button button, Color original, Color newColor, int i) {
        System.out.println("flashButtons called");
        button.setBackground(new Background(new BackgroundFill(newColor, null, null)));
        new AnimationTimer() {
            long startTime;

            public void myStart() {
                startTime = System.nanoTime();
                start();
            }


            @Override
            public void handle(long now) {
                if (now - startTime >= (long) 1.5e9) {

                    System.out.println("animating");
                    button.setBackground(new Background(new BackgroundFill(original, null, null)));
                    stop();
                    flashColors(i+1);
                }
            }
        }.myStart();
    }

    /**
     * Will flash the button the user was supposed to click in black
     */
    private void gameOver() {
       SimonBoard.Color c = board.getIncorrectColor(); //get the color they were supposed to pick
        switch (c) {
            case GREEN: // will flash the green button black
                flashButton(greenButton, Color.GREEN, Color.rgb(0, 0, 0));
                break;
            case RED: // will flash the red button black
                flashButton(redButton, Color.RED, Color.rgb(0, 0, 0));
                break;
            case YELLOW: // will flash the yellow button black
                flashButton(yellowButton, Color.YELLOW, Color.rgb(0, 0, 0));
                break;
            case BLUE: // will flash the blue button black
                flashButton(blueButton, Color.BLUE, Color.rgb(0, 0, 0));
                break;
            }
        // will display a pop-up so the user can leave the game
         Optional<ButtonType> o = new Alert(Alert.AlertType.CONFIRMATION, "Game over ").showAndWait();
        if (!o.isPresent()) {
            mainApp.openView(new FXMLLoader(getClass().getResource("../view/Simon.fxml")));
        }
        o.ifPresent(new Consumer<ButtonType>() {
            @Override
            public void accept(ButtonType buttonType) {
                if (buttonType.equals(ButtonType.OK)) {
                    mainApp.openView(new FXMLLoader(getClass().getResource("../view/Simon.fxml")));
                } else if (buttonType.equals(ButtonType.CANCEL)) {

                }
            }
        });
        }
    }