package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import model.GamePlayer;
import model.hangman.HangmanBoard;
import model.hangman.HangmanMove;
import model.hangman.HumanHangmanPlayer;

import java.util.*;
import java.util.function.Consumer;

public class HangmanGameController extends Controller {

    private static final String WORD_LIST_PATH = "file/HangmanWords.txt";

    private GamePlayer<HangmanMove> player0; // this player
    private HangmanBoard board;
    private String randomWord;
    private List<Line> lines;

    @FXML
    private GridPane wordLetters, incorrectLetters;
    // word letters are the correct letters in the bottom
    // incorrect letters are incorrect guesses at the top

    @FXML
    private Button confirm; // the button to confirm the user's choice

    @FXML
    private LimitedCharacterField textField; // won't let users enter previously entered letters or invalid characters

    @FXML
    private Circle head; // head of the stick figure

    @FXML
    private Line torso, arm1, arm2, leg1, leg2; // limbs of the stick figure


    @FXML
    /**
     * Will randomly generate a word
     */
    private void initialize() {
        try {
            ArrayList<String> words = new ArrayList<>(); // array list for text file
            Random rand = new Random();
            Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(WORD_LIST_PATH));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                words.add(line); //adds words to the text file
            }
            int i = rand.nextInt(words.size()); // generates random int
            randomWord = words.get(i).toUpperCase(); // picks word at randomly generated int
        } catch (Exception e) {
            e.printStackTrace();
        }

        wordLetters.setHgap(50); // sets the gap of the letter lines
        wordLetters.setVisible(true); // so we can see the lines at the bottom

        lines = new ArrayList<>();
        for (int i = 0; i < randomWord.length(); i++) { // draws as many lines as word as characters
            Line line = new Line();
            line.setStartX(0); // draws a line 40 units in x
            line.setEndX(40);
            line.setStartY(50); // no vertical distance up
            line.setEndY(50);
            lines.add(line);
            wordLetters.add(line, i, 0); // adds the lines to the gridpane where correct letters will go

        }

        board = new HangmanBoard(randomWord); // creates hangman board with randomly generated word
    }

    private void reset() {

    }

    public void setPlayers(GamePlayer<HangmanMove> player0) {
        this.player0 = player0;
        start();
    }

    /**
     * Starts game by starting a turn
     */
    public void start() {
        doTurn();
    }

    /**
     * When the game is over, the text will read you win or you lose
     * The user can choose to play again or quit to the main menu
     */
    private void gameOver() {
        Optional<ButtonType> o = new Alert(Alert.AlertType.CONFIRMATION, "Game over, " +
                (board.places().get(1) == 1 ? "You Win!" : "You Lose!")).showAndWait();
        if (!o.isPresent()) {
            mainApp.openView(new FXMLLoader(getClass().getResource("/view/MainMenu.fxml")));
        }
        o.ifPresent(new Consumer<ButtonType>() {
            @Override
            public void accept(ButtonType buttonType) {
                if (buttonType.equals(ButtonType.OK)) {
                    mainApp.openView(new FXMLLoader(getClass().getResource("/view/Hangman.fxml")));
                } else if (buttonType.equals(ButtonType.CANCEL)) {
                    mainApp.openView(new FXMLLoader(getClass().getResource("/view/MainMenu.fxml")));
                }
            }
        });
    }

    /**
     * Does a turn until the game is over
     * Will make a move with the board and a certain move
     */
    private void doTurn() {
        GamePlayer<HangmanMove> currentTurn = player0;
        if (currentTurn instanceof HumanHangmanPlayer) {
            ((HumanHangmanPlayer)currentTurn).setNodes(confirm, textField);
        }
        currentTurn.makeMove(board, (HangmanMove m) -> {
            board.makeMove(m);
            updateView(m);
            if (board.gameOver()) {
                gameOver();
            } else {
                doTurn();
            }
        });
    }

    /**
     * Changes the label stating your turn or opponents turn and the current piece
     *
     * @param m The current move
     */
    private void updateView(HangmanMove m) {
        if (board.isCorrectMove(m)) {
            for (Integer i : board.getAllPositions(m)) {
                lines.get(i).setVisible(false);
                wordLetters.add(new Label("" + m.getCharacter()), i, 0);
            }
        } else {
                incorrectLetters.add(new Label("" + m.getCharacter()), board.getNumMistakes(), 0);
            switch (board.getNumMistakes()) {
                case 1: // one guess wrong draws head
                    head.setVisible(true);
                    break;
                case 2: // two guesses wrong draws torso
                    torso.setVisible(true);
                    break;
                case 3: // three guesses wrong draws first arm
                    arm1.setVisible(true);
                    break;
                case 4: // four guesses wrong draws second arm
                    arm2.setVisible(true);
                    break;
                case 5: // five guesses wrong draws first leg
                    leg1.setVisible(true);
                    break;
                case 6: // six guesses wrong draws second leg
                    leg2.setVisible(true);
                    break;
            }
        }
    }
}