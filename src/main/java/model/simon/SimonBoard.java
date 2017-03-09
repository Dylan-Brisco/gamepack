package model.simon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dylanbrisco on 1/23/17.
 */
public class SimonBoard {
    /**
     * Number sequence
     * Current sequence
     * User colors on current sequence
     */

    /**
     * The colors of the Simon Game
     */
    public enum Color {
        GREEN, RED, YELLOW, BLUE
    }

    private Random random = new Random(); // for the random generation of a color

    boolean gameOver = false; // is the game currently over

    boolean sequenceOver = false; // if the board is currently flashing colors

    private static final int NUMBER_OF_Colors = 4; // total number of colors: Green, Red, Yellow, Blue

    private int userIndex = 0; // the amount of plays into the game the user is

    private List<Color> currentSequence; // the randomly generated sequence of colors

    /**
     * Returns the list of colors representing current game
     *
     * @return currentSequence
     */
    public List<Color> getCurrentSequence() {

        return this.currentSequence;
    }

    /**
     * SimonBoard is represented as a list of randomly generated colors
     */
    public SimonBoard() {
        currentSequence = new ArrayList<>();
        currentSequence.add(generateRandomColor());
    }

    /**
     * Returns a randomly generated color
     *
     * @return Color
     */
    public Color generateRandomColor() {
        int rand = random.nextInt(NUMBER_OF_Colors); // creates a random number between 0 and 3
        switch (rand) {
            case 0: // if rand is 0 random color is green
                return Color.GREEN;
            case 1: // if rand is 1 random color is red
                return Color.RED;
            case 2: // if rand is 2 random color is yellow
                return Color.YELLOW;
            case 3: // if rand is 3 random color is blue
                return Color.BLUE;
            default: // just in case anything goes wrong
                return null;
        }
    }

    /**
     * Will make a move based on a user selected color
     *
     * @param color
     */
    public void makeMove(Color color) {
        sequenceOver = false;
        if (currentSequence.get(userIndex) == (color)) { // checks if the user got the correct color of the sequence
            if(currentSequence.size() - 1 == userIndex) {  // checks if the user is currently on last color of the sequence
                currentSequence.add(generateRandomColor()); // if user on last color add a new color to the end of sequence
                sequenceOver = true; // will allow for re-flashing of buttons
                userIndex = 0; // reset userIndex
            }
            else { // if user gets color correct and is not currently on the last color of a sequence
                userIndex++; // increment the userIndex so makeMove will check against the next color in the sequence
            }
        } else { // if user gets the color incorrect gameOver is set to true so game can end.
            gameOver = true;
        }
    }

    /**
     * Returns a boolean indicating if the game is over
     *
     * @return gameOver
     */

    public boolean isGameOver() {

        return gameOver;
    }


    /**
     * Returns a boolean indicating if the sequence is over
     *
     * @return sequenceOver
     */
    public boolean isSequenceOver() {
        return sequenceOver;
    }

    /**
     * Returns the correct color that the user did not guess so that it can be flashed
     *
     * @return Color
     */
    public Color getIncorrectColor() {
        if(!isGameOver()) { // if the game is not over do nothing
            return null;
        }
        else { // if the game is over then return the correct color
            return currentSequence.get(userIndex);
        }
    }
}