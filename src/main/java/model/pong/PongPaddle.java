package model.pong;

/**
 * Created by dylanbrisco on 2/8/17.
 */
public class PongPaddle {

    private static final int PADDLE_MOVEMENT = 4; // how many units the paddle will move down

    private double height; // height of paddle
    private double width; // width of paddle
    public static final double PADDLE_LENGTH = 10;
    public static final double PADDLE_WIDTH = 1.75;

    public PongPaddle(double height, double width) {
        this.height = height;
        this.width = width;
    }

    /**
     * Directions the paddle can move
     * Up moves up
     * Down moves down
     * None is neither
     */
    public enum Direction {
        UP, DOWN, NONE
    }

    /**
     * Will update the pong paddle in the given direction
     * @param d direction we want the paddle to move in
     */
    public void updateDirection(Direction d) {
        if(d == Direction.DOWN) {
            height += PADDLE_MOVEMENT; // moves paddle down
        }
        if(d == Direction.UP) {
            height -= PADDLE_MOVEMENT; // moves paddle up
        }

        if(d == Direction.NONE) {
            height += 0; // keeps paddle in same location
        }
        if(height + PADDLE_LENGTH/2 > PongBoard.BOARD_HEIGHT) {
            height = PongBoard.BOARD_HEIGHT - PADDLE_LENGTH/2; // makes sure paddle does not go off screen
        }
        if(height - PADDLE_LENGTH/2 < 0) {
            height = PADDLE_LENGTH/2; // makes sure paddle does not go off screen
        }
    }

    public void setPaddleY(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}