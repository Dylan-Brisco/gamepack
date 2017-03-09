package model.pong;

import java.util.Random;

/**
 * Created by dylanbrisco on 2/8/17.
 */
public class PongBoard {

    public static final double BOARD_WIDTH = 100;  // board width
    public static final double BOARD_HEIGHT = 100; // board height
    private int scorePaddle1 = 0; // score of player1
    private int scorePaddle2 = 0; // score of player2
    private int scoredLast; // which player scored last
    public static final double MAX_VELOCITY = 7; // the max speed the ball can move
    public static final double HALF_MAX_ANGLE = Math.PI / 12.0; // max angle the ball can be launched

    private PongPaddle paddle1;
    private PongPaddle paddle2;
    private PongBall ball;
    private Random rand; // used to generate angle of release

    /**
     * PongBoard has 2 paddles and a ball
     * rand is used to determine the angle the ball is to be released
     * rand is also used to determine which player is served the ball first
     */
    public PongBoard() {
        paddle1 = new PongPaddle(BOARD_HEIGHT / 2, 10);
        paddle2 = new PongPaddle(BOARD_HEIGHT / 2, BOARD_WIDTH - 10);
        ball = new PongBall(BOARD_WIDTH / 2, BOARD_HEIGHT / 2, 0, 0);
        rand = new Random();
        scoredLast = rand.nextInt() + 1; // used to determine who is served the ball first
    }

    /**
     * Returns a boolean representing if round is over
     * @return true if round over, false otherwise
     */
    public boolean isRoundOver() {
        return ball.getX() > BOARD_WIDTH || ball.getX() < 0;
    }

    /**
     * Adds a point to the winner of the round
     * Resets the board by centering paddles and ball
     * Relauches the ball
     * If player's score is greater than ten, then they win
     */
    public void endRound() {
        if (getRoundWinner() == 1) {
            scorePaddle1++;
            scoredLast = 1;
        } else {
            scorePaddle2++;
            scoredLast = 2;
        }
        resetBoard();
        if (!isGameOver()) {
            startRound();
        } else {
            getGameWinner();
        }
    }

    /**
     * Returns the winner of a round
     * @return 2 if ball goes past paddle 1, 1 if ball goes past paddle 2
     *
     */
    public int getRoundWinner() {
        if (ball.getX() <= 0) {
            return 2;
        }
        if (ball.getX() >= BOARD_WIDTH) {
            return 1;
        }
        return 0;
    }

    /**
     * Returns the winner of the game
     * @return an int representing game winner
     */
    public int getGameWinner() {
        if (scorePaddle1 == 10) {
            return 1;
        }
        if (scorePaddle2 == 10) {
            return 2;
        }
        return 0;
    }

    /**
     * If a player's score is greater than 10 then they win the game
     * @return
     */
    public boolean isGameOver() {
        return scorePaddle1 == 10 || scorePaddle2 == 10;
    }

    /**
     * Begins the round by centering the ball then randomly releasing it
     */
    public void startRound() {
        ball.setX(BOARD_WIDTH / 2); // centers x
        ball.setY(BOARD_WIDTH / 2); // centers y
        launchBall();
    }

    /**
     * Will move the paddles
     * Checks for colision and deflects the ball
     * @param paddle1Direction direction in which paddle1 is moving
     * @param paddle2Direction direction in which paddle2 is moving
     */
    public void updateBoard(PongPaddle.Direction paddle1Direction, PongPaddle.Direction paddle2Direction) {
        paddle1.updateDirection(paddle1Direction);
        paddle2.updateDirection(paddle2Direction);

        ball.setX(ball.getX() + ball.getVelocityX()); // updates the ball location x
        ball.setY(ball.getY() + ball.getVelocityY()); // updates the ball location y

        double ballLeftX = ball.getX() - PongBall.RADIUS; // left side of the ball
        double ballRightX = ball.getX() + PongBall.RADIUS; // right side of the ball
        double ballBottom = ball.getY() + PongBall.RADIUS; // bottom on the ball
        double ballTop = ball.getY() - PongBall.RADIUS; // top of the ball

        double leftPaddle1 = paddle1.getWidth() - PongPaddle.PADDLE_WIDTH / 2; //left paddle left side
        double leftPaddle2 = paddle2.getWidth() - PongPaddle.PADDLE_WIDTH / 2; // right paddle left side
        double rightPaddle1 = paddle1.getWidth() + PongPaddle.PADDLE_WIDTH / 2; // left paddle right side
        double rightPaddle2 = paddle2.getWidth() + PongPaddle.PADDLE_WIDTH / 2; // right paddle right side

        double topPaddle1 = paddle1.getHeight() - PongPaddle.PADDLE_LENGTH / 2; //left paddle top
        double topPaddle2 = paddle2.getHeight() - PongPaddle.PADDLE_LENGTH / 2; //right paddle top
        double bottomPaddle1 = paddle1.getHeight() + PongPaddle.PADDLE_LENGTH / 2; //left paddle bottom side
        double bottomPaddle2 = paddle2.getHeight() + PongPaddle.PADDLE_LENGTH / 2; //right paddle bottom side

       if(colliding(leftPaddle1, rightPaddle1, bottomPaddle1, topPaddle1, ballLeftX, ballRightX, ballBottom, ballTop)) {
            // checks for collision of the left paddle
            ball.setVelocityX(-ball.getVelocityX()); // reflects the ball
            while (colliding(leftPaddle1, rightPaddle1, bottomPaddle1, topPaddle1, ballLeftX, ballRightX, ballBottom, ballTop)) {
                ballLeftX = ball.getX() - PongBall.RADIUS; // updates ball locations
                ballRightX = ball.getX() + PongBall.RADIUS;
                ballBottom = ball.getY() + PongBall.RADIUS;
                ballTop = ball.getY() - PongBall.RADIUS;
                ball.setX(ball.getX() + ball.getVelocityX()); // updates the ball location x
                ball.setY(ball.getY() + ball.getVelocityY()); // updates the ball location y
            }

        } else if (colliding(leftPaddle2, rightPaddle2, bottomPaddle2, topPaddle2, ballLeftX, ballRightX, ballBottom, ballTop)) {
            // checks for collision of the right paddle
            ball.setVelocityX(-ball.getVelocityX()); // reflects the ball
            while (colliding(leftPaddle1, rightPaddle1, bottomPaddle1, topPaddle1, ballLeftX, ballRightX, ballBottom, ballTop)) {
                ballLeftX = ball.getX() - PongBall.RADIUS; // updates ball locations
                ballRightX = ball.getX() + PongBall.RADIUS;
                ballBottom = ball.getY() + PongBall.RADIUS;
                ballTop = ball.getY() - PongBall.RADIUS;
                ball.setX(ball.getX() + ball.getVelocityX()); // updates the ball location x
                ball.setY(ball.getY() + ball.getVelocityY()); // updates the ball location y
            }
        } else if (ballTop < 0) {
            // checks for collision with the top of the screen
            ball.setVelocityY(-ball.getVelocityY()); // deflect y velocity
            while (ballTop < 0) {
                ballLeftX = ball.getX() - PongBall.RADIUS;
                ballRightX = ball.getX() + PongBall.RADIUS;
                ballBottom = ball.getY() + PongBall.RADIUS;
                ballTop = ball.getY() - PongBall.RADIUS;
                ball.setX(ball.getX() + ball.getVelocityX()); // update x location
                ball.setY(ball.getY() + ball.getVelocityY()); // update y location
            }
        } else if (ballBottom > BOARD_HEIGHT) {
            // checks for collision with the bottom of the screen
            ball.setVelocityY(-ball.getVelocityY()); // deflect y velocity
            while (ballTop > BOARD_HEIGHT) {
                ballLeftX = ball.getX() - PongBall.RADIUS;
                ballRightX = ball.getX() + PongBall.RADIUS;
                ballBottom = ball.getY() + PongBall.RADIUS;
                ballTop = ball.getY() - PongBall.RADIUS;
                ball.setX(ball.getX() + ball.getVelocityX());  // update x velocity
                ball.setY(ball.getY() + ball.getVelocityY()); // update y velocity
            }
        }

        if (isRoundOver()) { // if round is over then end round and reset board
            endRound();
        }
    }

    /**
     * Centers the paddles
     * Centers the ball
     */
    public void resetBoard() {
        ball.setX(BOARD_WIDTH / 2);
        ball.setY(BOARD_HEIGHT / 2);
        paddle1.setPaddleY(BOARD_HEIGHT / 2);
        paddle2.setPaddleY(BOARD_HEIGHT / 2);
    }

    /**
     * Will launch the ball at a random angle towards the person who scored last
     */
    public void launchBall() {
        double angle = rand.nextDouble() * 2.0 * HALF_MAX_ANGLE - HALF_MAX_ANGLE;
        if (scoredLast == 1) {
            angle += Math.PI;
        }
        ball.setVelocityX(.5 * MAX_VELOCITY * Math.cos(angle)); // randomly generated x velocity
        ball.setVelocityY(.5 * MAX_VELOCITY * Math.sin(angle)); // randomly generated y velocity
    }

    /**
     * Returns the height of paddle1
     * @return paddle1 height
     */
    public double getPaddle1Height() {
        if (paddle1 != null) {
            return paddle1.getHeight();
        }
        return BOARD_HEIGHT / 2;
    }

    /**
     * Returns the height of paddle2
     * @return paddle2 height
     */
    public double getPaddle2Height() {
        if (paddle2 != null) {
            return paddle2.getHeight();
        }
        return BOARD_HEIGHT / 2;
    }

    /**
     * Returns the x location of the ball
     * @return ball x location
     */
    public double getBallX() {
        if (ball != null) {
            return ball.getX();
        }
        return BOARD_HEIGHT / 2;
    }

    /**
     * Returns the y location of the ball
     * @return ball y location
     */
    public double getBallY() {
        if (ball != null) {
            return ball.getY();
        }
        return BOARD_HEIGHT / 2;
    }

    /**
     * Returns the score of paddle1
     * @return scorePaddle1
     */
    public int getPlayer1Score() {
        return scorePaddle1;
    }

    /**
     * Returns the score of paddle2
     * @return scorePaddle2
     */
    public int getPlayer2Score() {
        return scorePaddle2;
    }

    /**
     * Returns true if the paddle and the ball are colliding
     * @param left1 left side of paddle
     * @param right1 right side of paddle
     * @param bottom1 bottom of paddle
     * @param top1 top of paddle
     * @param left2 left side of ball
     * @param right2 right side of ball
     * @param bottom2 bottom of ball
     * @param top2 top of ball
     * @return true if colliding, false otherwise
     */
    public boolean colliding(double left1, double right1, double bottom1, double top1, double left2, double right2, double bottom2, double top2) {
        return right1 >= left2 && right2 >= left1 && bottom1 >= top2 && bottom2 >= top1;
    }
}