package model.snake;

import javafx.util.Pair;
import java.util.List;
import java.util.Random;

/**
 * Created by dylanbrisco on 1/16/17.
 */
public class SnakeBoard {

    private int width; // width of board
    private int height; // height of board
    private int foodX; // x location of food
    private int foodY; // y location of food
    private int score; // the score

    private Random random; // used to randomly generate food locations and starting location of snake
    private Snake s;

    /**
     * Creates a SnakeBoard with a specific width and height
     * Creates a snake and places it at random location on the board
     * Adds a piece of food at a random location on the board
     * @param width width of board
     * @param height height of board
     */
    public SnakeBoard(int width, int height) {
        this.height = height;
        this.width = width;
        random = new Random();
        s = new Snake(new Pair<>(random.nextInt(width), random.nextInt(height)));
        // randomly generates the starting location of the snake
        updateFood(); // places food
    }

    /**
     * Randomly genetrates x location for food
     * Randomly generates y location for food
     */
    private void updateFood() {
        foodX = random.nextInt(width);
        foodY = random.nextInt(height);

        while(s.getPosition().contains(new Pair<> (foodX, foodY))) {
            foodX = random.nextInt(width);
            foodY = random.nextInt(height);
        }
    }

    /**
     * Will update the board based on the direction the snake is currently moving in
     * If snake eats the food, makes snake larger and randomly places food on the board
     * @param d the direction the snake is moving
     */
    public void updateBoard(Snake.Direction d) {
        s.update(d);
        List<Pair<Integer,Integer>> snakePos = s.getPosition();

        if(snakePos.get(0).getKey() == foodX && snakePos.get(0).getKey() <= foodX  &&
            snakePos.get(0).getValue() >= foodX && snakePos.get(0).getValue() <= foodX ) {
            updateFood();
            s.addPartsLeftToGrow(5);
            score += 5;
        }
    }

    /**
     * Returns the width of the board
     * @return the width of the board
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the board
     * @return the height of the board
     */
    public int getHeight() {
        return height;
    }

    /**
     * Determines if the game is over
     * @return true if game is over, false otherwise
     */
    public boolean isGameOver() {
        List<Pair<Integer,Integer>> snakePos = s.getPosition();
        int x = snakePos.get(0).getKey();
        int y = snakePos.get(0).getValue();

        if(x >= width || x <= 0 || y >= height || y <= 0) { //snake out of bounds
            return true;
        }
        snakePos.remove(0);
        if(snakePos.contains(new Pair<>(x,y))) { //snake eats itself
            return true;
        }
        if(snakePos.size()+1 == width * height) { //snake takes up whole screen
            return true;
        }
        return false;
    }

    public boolean isWinner() {
        return s.getPosition().size() == width * height; //wins if the snake takes up the entire screen
    }

    /**
     * Returns the top portion of the snake (head)
     * @return the head of the snake
     */
    public List<Pair<Integer, Integer>> getSnakePosition() {
        return s.getPosition();
    }

    /**
     * Returns pair containing x and y location of the food
     * @return pair
     */
    public Pair<Integer, Integer> getFoodPos() {
        return new Pair<>(foodX, foodY);
    }

    /**
     * Returns the current score
     * @return score
     */
    public int getScore() {
        return score;
    }

}