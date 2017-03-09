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
import javafx.util.Pair;
import model.snake.Snake;
import model.snake.SnakeBoard;

public class SnakeGameController extends Controller {

    @FXML
    private Canvas snakeCanvas;

    @FXML
    private BorderPane snakeBorder; // boarder we cannot pass

    @FXML
    private Text scoreText; // displays the score

    private Snake.Direction lastPressed;

    private GraphicsContext gc;

    private SnakeBoard snakeBoard = new SnakeBoard(40,40); //grid

    private boolean calledYet = false;

    private boolean gameOver = false;

    private AnimationTimer timer;

    @FXML
    public void initialize() {
    BorderWidths widths = new BorderWidths(5);
        snakeBorder.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                widths))); // draws the boarder of the game

        gc = snakeCanvas.getGraphicsContext2D(); // so we can draw
        updateCanvas();

            timer = new AnimationTimer() {
                private long lastUpdated = 0L;

                @Override
                public void handle(long now) {
                    if (now - lastUpdated >= 4e7) { // how fast the refresh rate is in nanoseconds
                        lastUpdated = now;
                        snakeBoard.updateBoard(lastPressed); // will account for arrow key presses, changing snake direction
                        updateCanvas(); // redraws the canvas
                        if (snakeBoard.isGameOver()) { // ends the game if the game is over
                            gameOver = true;
                            stop();
                            gameOver();
                            stop();
                        }
                    }
                }
            };
        }

    @FXML
    public void handleKeyPressed(KeyEvent event) {
        KeyCode c = event.getCode();
        if(c.isArrowKey()) {

            if(c == KeyCode.UP && lastPressed != Snake.Direction.DOWN) { // if user clicks up and snake is not moving down
                lastPressed = Snake.Direction.UP; // change snake direction to up
            }
            if(c == KeyCode.DOWN && lastPressed != Snake.Direction.UP) { // if user clicks down and snake is not moving up
                lastPressed = Snake.Direction.DOWN; // change snake direction to down
            }
            if(c == KeyCode.LEFT && lastPressed != Snake.Direction.RIGHT) { // if user clicks left and snake is not moving right
                lastPressed = Snake.Direction.LEFT; // change snake direction to left
            }
            if(c == KeyCode.RIGHT && lastPressed != Snake.Direction.LEFT) { // if user clicks right and snake is not moving left
                lastPressed = Snake.Direction.RIGHT; // change snake direction to right
            }

            if (!calledYet && !gameOver) {
                calledYet = true;
                timer.start();
            }
        }
        event.consume();
    }


    private void gameOver() {
        timer.stop();

       /* Optional<ButtonType> o = new Alert(Alert.AlertType.CONFIRMATION, "Game over, " +
               (snakeBoard.getScore())).showAndWait();
        if (!o.isPresent()) {
            mainApp.openView(new FXMLLoader(getClass().getResource("/view/Snake.fxml")));
        }
        o.ifPresent(new Consumer<ButtonType>() {
            @Override
            public void accept(ButtonType buttonType) {
                if (buttonType.equals(ButtonType.OK)) {
                    reset(); */
               // } else if (buttonType.equals(ButtonType.CANCEL)) {
                    mainApp.openView(new FXMLLoader(getClass().getResource("/view/Snake.fxml")));
                }



    private void reset() {

    }

    private void updateCanvas() {
        gc.clearRect(0,0,400,400); // will clear the screen
        gc.setFill(Color.BLACK); // sets color we are drawing in to black
        for(Pair<Integer,Integer> pair : snakeBoard.getSnakePosition()) {
            gc.fillRect(pair.getKey()*10, pair.getValue()*10, 10, 10); // redraws snake, scaled to screen
        }
        gc.setFill(Color.BLUE); // sets color we are drawing in to blue
        int foodX = snakeBoard.getFoodPos().getKey();
        int foodY = snakeBoard.getFoodPos().getKey();
        gc.fillRect(foodX*10, foodY*10, 10, 10); // draws food, scaled to screen
        scoreText.setText("Score: " + snakeBoard.getScore()); // will display the new score
    }
}