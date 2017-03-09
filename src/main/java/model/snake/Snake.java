package model.snake;

import javafx.util.Pair;

import java.util.*;

/**
 * Created by dylanbrisco on 1/11/17.
 */
public class Snake {
    // the x and y coordinates of the snake
   private List<Pair<Integer, Integer>> snakePos;

    /**
     * The directions in which the snake can move
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // for when we eat food
    private int partsLeftToGrow = 0;

    // instantiate the snake when it eats
    public Snake(List<Pair<Integer, Integer>> s, int partsLeft) {
        this.snakePos = s;
        this.partsLeftToGrow = partsLeft;
    }

    // when we first create the snake
    public Snake(Pair<Integer, Integer> start) {
        snakePos = new ArrayList<>();
        snakePos.add(start);
    }

    /**
     * Will update the postion the snake is in
     * @param d the direction the snake is currently moving
     */
    public void update(Direction d) {
        Pair<Integer,Integer> nextHead = null;
        if(d == Direction.UP) { // if snake is moving up
            nextHead = new Pair<>(snakePos.get(0).getKey(), snakePos.get(0).getValue()-1);
            // y is getting smaller, so subtract 1 from y value
        }
        if(d == Direction.DOWN) { // if snake is moving down
            nextHead = new Pair<>(snakePos.get(0).getKey(), snakePos.get(0).getValue()+1);
            // y is getting larger, so add 1 to y value
        }
        if(d == Direction.LEFT) { // if snake is moving left
            nextHead = new Pair<>(snakePos.get(0).getKey()-1, snakePos.get(0).getValue());
            // x is getting smaller, so subtract 1 from x value
        }
        if(d == Direction.RIGHT) { // if snake is moving right
            nextHead = new Pair<>(snakePos.get(0).getKey()+1, snakePos.get(0).getValue());
            // x is getting larger, so add 1 to x value
        }
        snakePos.add(0, nextHead);
        if(partsLeftToGrow == 0) {
            snakePos.remove(snakePos.size() - 1);
        } else {
            partsLeftToGrow--;
        }
    }

    /**
     * Will add length to the snake after it eats food
     * @param x the amount of units we are growing the snake
     */
    public void addPartsLeftToGrow(int x) {
        partsLeftToGrow += x;
    }

    public List<Pair<Integer, Integer>> getPosition() {
        return new ArrayList<>(snakePos);
    }
}