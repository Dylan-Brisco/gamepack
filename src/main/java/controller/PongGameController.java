package controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.pong.PongBall;
import model.pong.PongBoard;
import model.pong.PongPaddle;

/**
 * Created by dylanbrisco on 1/31/17.
 */
public class PongGameController extends Controller {

    @FXML
    private Canvas pongCanvas;

    @FXML
    private Text scoreText1, scoreText2; // scores of the players

    @FXML
    private BorderPane pongBorder; // for deflecting ball if it hits the top or bottom of screen

    private boolean calledYet = false;

    private boolean gameOver = false;

    private PongBoard pongBoard;

    private GraphicsContext gc; // so we can draw

    private PongPaddle.Direction paddle1Dir, paddle2Dir; // directions paddles are moving

    private AnimationTimer timer;

    @FXML
    /**
     * Creates the pong board
     * handles the animation
     */
    public void initialize() {
        pongBoard = new PongBoard(); // the current board

        pongBorder.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));
        gc = pongCanvas.getGraphicsContext2D(); // so the board can be updated

        updateCanvas();
        pongBoard.startRound(); // begins the game
        timer = new AnimationTimer() { // for animation and refresh
            private long lastUpdated = 0L;

            @Override
            public void handle(long now) {
                if (now - lastUpdated >= 4e7) { // the refresh rate
                    lastUpdated = now;
                    pongBoard.updateBoard(paddle1Dir, paddle2Dir); // will update the board based on paddle directions
                    paddle1Dir = PongPaddle.Direction.NONE;
                    paddle2Dir = PongPaddle.Direction.NONE; // will only move paddle when key is held down
                    updateCanvas();
                    if (pongBoard.isGameOver()) { // if the game is over stop the animation and end the game
                        gameOver = true;
                        stop();
                        gameOver();
                        stop();
                    }
                }
            }
        };
    }

    /**
     * If the game is over stop the animation
     */
    private void gameOver() {
        timer.stop(); // stops the animation
        gameOver = true;
        mainApp.openView(new FXMLLoader(getClass().getResource("../view/Pong.fxml")));
    }


    @FXML
    /**
     * Will change the direction of the paddles based on key presses
     */
    public void handleKeyPressed(KeyEvent event) {
        KeyCode c = event.getCode();
            if (c == KeyCode.UP) { // will move paddle 1 up
                paddle1Dir = PongPaddle.Direction.UP;
            }
            if (c == KeyCode.DOWN) { // will move paddle 1 down
                paddle1Dir = PongPaddle.Direction.DOWN;
            }
            if (c == KeyCode.W) { // will move paddle 2 up
                paddle2Dir = PongPaddle.Direction.UP;
            }
            if (c == KeyCode.S) { // will move paddle 2 down
                paddle2Dir = PongPaddle.Direction.DOWN;
            }

            if (!calledYet && !gameOver) {
                calledYet = true; // starts animation
                timer.start();
            }

        if(pongBoard.isRoundOver()) { // if round is over set the direction of the paddles to none
            paddle1Dir = PongPaddle.Direction.NONE; // paddle 1 does not move
            paddle2Dir = PongPaddle.Direction.NONE; // paddle 2 does not move
        }
        event.consume();
    }

    /**
     * Will redraw the scene
     * Redraws paddles
     * Redraws ball
     */
    private void updateCanvas() {
        gc.clearRect(0,0,400,400);  // clears scene
        gc.setFill(Color.BLACK); // sets color we are drawing with to black
        gc.fillRect(40, 4 * pongBoard.getPaddle1Height(),4 * PongPaddle.PADDLE_WIDTH, 4 * PongPaddle.PADDLE_LENGTH); // draws paddle1
        gc.fillRect(4 * (PongBoard.BOARD_WIDTH - 10), 4 * pongBoard.getPaddle2Height(), 4 * PongPaddle.PADDLE_WIDTH, 4 * PongPaddle.PADDLE_LENGTH); //draws paddle 2
        gc.fillOval(4 * pongBoard.getBallX(), 4 * pongBoard.getBallY(), 2 * PongBall.RADIUS, 2 * PongBall.RADIUS); // draws ball
        scoreText1.setText("" + pongBoard.getPlayer1Score()); // displays the score of player 1
        scoreText2.setText("" + pongBoard.getPlayer2Score()); // displays the score of player 2
    }
}