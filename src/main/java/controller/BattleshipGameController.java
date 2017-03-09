package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import model.battleship.BattleshipGrid;
import model.battleship.Ship;

import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Created by dylanbrisco on 3/3/17.
 */
public class BattleshipGameController extends Controller {
    int totalTimesPlayerHit = 0; // how many times player was hit
    int totalTimesComputerHit = 0; // how many times the computer was hit

    BattleshipGrid playerGrid; // the player's board at the bottom
    BattleshipGrid computerGrid; // the computer's board at the top
    Random rand; // to generate random shots and ship locations
    Pane[][] computerNodes; // used for background
    Pane[][] playerNodes;

    @FXML
    GridPane playerGridPane, computerGridPane;

    @FXML
    NumberTextField userRowInput, userColInput;

    @FXML
    Button confirmChoice;

    @FXML
    private void initialize() {
        computerNodes = new Pane[9][9]; // board is 9 by 9
        playerNodes = new Pane[9][9];
        confirmChoice = new Button();
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                Pane p1 = new Pane();
                Pane p2 = new Pane();
                computerGridPane.add(p1, i, j); // adds panes to the boards
                computerNodes[i][j] = p1;

                playerGridPane.add(p2, i, j);
                playerNodes[i][j] = p2; // adds panes to the board
            }
        }
        playerGrid = new BattleshipGrid();

        computerGrid = new BattleshipGrid();

        randomlyPlaceShips(computerGrid); // randomly assign ships
        //colorComputerShips(); // used for testing
       setPlayerShips(); // will ask for input
        colorPlayerShips(); // shades ships grey
    }

    public void doTurn() {
        if (totalTimesComputerHit < 17 && totalTimesPlayerHit < 17) { // max total hits is 17 then all ships sunk
            doHumanTurn(); // player takes shot
        }
        else {
            endGame(); // if all hit, then end game
        }
    }

    /**
     * Will read row and column input from textfields when fire is pressed
     * Will determine if that spot on computer grid has a ship part
     * If it does, then it sets the background of that location red
     * If it is a miss it is set to black
     */
    public void doHumanTurn() {
        boolean validHit = false;
        int rowInput = -1;
        int colInput = -1;
        while (rowInput == -1 && colInput == -1) {
            boolean validInput = true;
            rowInput = 0;
            colInput = 0;

            if (userRowInput.getCharacters() != null && userRowInput.getText().length() == 1) {
                rowInput = Integer.parseInt(userRowInput.getText()) - 1;
                if (rowInput < 0 || rowInput > 9) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Row Not Within Bounds");
                    alert.setContentText("Please Choose Another Row");
                    alert.showAndWait();
                    validInput = false;
                }
            }

            if (userColInput.getCharacters() != null && userColInput.getText().length() == 1) {
                colInput = Integer.parseInt(userColInput.getText()) - 1;
                if (colInput < 0 || colInput > 9) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Column Not Within Bounds");
                    alert.setContentText("Please Choose Another Location");
                    alert.showAndWait();
                    userRowInput.clear();
                    userColInput.clear();
                    validInput = false;
                }
            }

            if (computerGrid.getBox(colInput, rowInput).isHit() || computerGrid.getBox(colInput, rowInput).isMiss()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You Already Hit This Location");
                alert.setContentText("Please Choose Another Location");
                alert.showAndWait();
                validInput = false;
            } else {
                if (validInput) {
                    if (computerGrid.takeShot(colInput, rowInput)) {
                        totalTimesComputerHit++;
                        computerGrid.getBox(colInput, rowInput).setHit(true);
                        colorBoxHit(computerGrid, colInput, rowInput);
                    } else {
                        computerGrid.getBox(colInput, rowInput).setMiss(true);
                        colorBoxMiss(computerGrid, colInput, rowInput);
                    }
                }
                validHit = true;
            }
        }
        doComputerTurn();
    }

    /**
     * Will randomly generate a row and column location
     * Will see if there is a player ship part in row, col
     * If there is, then it is shaded red on grid
     * If there is not, then it is shaded black on grid
     */
    public void doComputerTurn() {
        boolean validInput = false;

        while (!validInput) {
            rand = new Random();
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);

            if (playerGrid.getBox(row, col).isHit() || playerGrid.getBox(row, col).isHit()) {
                validInput = false;
            }

            else {
                if (playerGrid.takeShot(row, col)) {
                    totalTimesPlayerHit++;
                    playerGrid.getBox(row, col).setHit(true);
                    colorBoxHit(playerGrid, row, col);
                } else {
                    playerGrid.getBox(row, col).setMiss(true);
                    colorBoxMiss(playerGrid, row, col);
                }
                validInput = true;
            }
        }
    }

    /**
     * Will color a location red if hit
     * @param grid the grid are attacking
     * @param row the row location of the box
     * @param col the column location of the box
     */
    public void colorBoxHit(BattleshipGrid grid, int row, int col) {
        if(grid.equals(computerGrid)) { // if computerGrid then shade computer nodes
            computerNodes[row][col].setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        }
        if(grid.equals(playerGrid)) { // if playerGrid then shade player nodes
            playerNodes[row][col].setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        }
    }

    /**
     * Will color a location black if miss
     * @param grid the grid we are attacking
     * @param row the row location of the box
     * @param col the column location of the box
     */
    public void colorBoxMiss(BattleshipGrid grid, int row, int col) {
        if(grid.equals(computerGrid)) { // if computerGrid then shade computer nodes
            computerNodes[row][col].setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        }
        if(grid.equals(playerGrid)) { // if playerGrid then shade player nodes
            playerNodes[row][col].setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        }
    }

    /**
     * Will ask if user wants to choose locations or randomly generate
     * If they randomly generate then randomlyPlaceShips(playerGrid) is called
     * Uses confirm dialogs to get input
     * If they enter input that makes ship go off board, reprompted
     * If they enter input that makes ship overlap with other ship, reprompted
     * Asked if they would like the ship vertical or horizontal
     */
    public void setPlayerShips() {
        boolean wantsRandom = false;
        ChoiceDialog<Boolean> dialog = new ChoiceDialog<>(false, true, false);
        dialog.setTitle("Battleship");
        dialog.setHeaderText("Randomly Place Ships?");
        dialog.setContentText("Choose Your Option: ");

        Optional<Boolean> result = dialog.showAndWait();
        if (result.isPresent()) {
            wantsRandom = result.get();
        }
        else {
            mainApp.openView(new FXMLLoader(getClass().getResource("/view/Battleship.fxml")));
        }
        if (wantsRandom) { // if they want randomly assigned positions
            randomlyPlaceShips(playerGrid);
        } else {
            Ship s;
            for (int i = 0; i < Ship.SHIPS.length; i++) {
                boolean isRotated = false;
                int rowStartLocation = 0;
                int colStartLocation = 0;

                ChoiceDialog<Boolean> rotate = new ChoiceDialog<>(false, true, false);
                rotate.setTitle("Battleship");
                rotate.setHeaderText(Ship.SHIPNAMES[i] + " Vertical ? ");
                rotate.setContentText("Choose Your Option: ");

                Optional<Boolean> wantRotated = rotate.showAndWait();
                if (wantRotated.isPresent()) {
                    isRotated = wantRotated.get();
                }
                else {
                    mainApp.openView(new FXMLLoader(getClass().getResource("/view/Battleship.fxml")));
                }

                boolean isValidShip = false;
                while (!isValidShip) { // while invalid ship location

                    ChoiceDialog<Integer> rowStartLoc = new ChoiceDialog<>(1, 2, 3, 4, 5, 6, 7, 8, 9);
                    rowStartLoc.initModality(Modality.NONE);
                    rowStartLoc.setTitle("Battleship");
                    rowStartLoc.setHeaderText("Row Start Of Your " + Ship.SHIPNAMES[i] + "[1,9]");
                    rowStartLoc.setContentText("Choose Your Option: "); // asks for row location

                    Optional<Integer> rowStart = rowStartLoc.showAndWait();
                    if (rowStart.isPresent()) {
                        rowStartLocation = rowStart.get() - 1; // board is 0-8 so must -1 from user input
                    }
                    else { // if they click cancel or X out then go to main menu
                        mainApp.openView(new FXMLLoader(getClass().getResource("/view/Battleship.fxml")));
                    }

                    ChoiceDialog<Integer> colStartLoc = new ChoiceDialog<>(1, 2, 3, 4, 5, 6, 7, 8, 9);
                    colStartLoc.setTitle("Battleship");
                    colStartLoc.setHeaderText("Column Start Of Your " + Ship.SHIPNAMES[i] + " [1,9]");
                    colStartLoc.setContentText("Choose Your Option: "); // asks for col location


                    Optional<Integer> colStart = colStartLoc.showAndWait();
                    if (colStart.isPresent()) {
                        colStartLocation = colStart.get() - 1;
                    }
                    else {
                        mainApp.openView(new FXMLLoader(getClass().getResource("/view/Battleship.fxml")));
                    }

                    if (isRotated) { // vertical
                        s = Ship.SHIPS[i];
                        s = s.shipRotate(); // rotate the ship
                        s.setGridRowStartLocation(rowStartLocation); // set row location
                        s.setGridColStartLocation(colStartLocation); // set column location
                    } else { // horizontal
                        s = Ship.SHIPS[i];
                        s.setGridRowStartLocation(rowStartLocation); // set row location
                        s.setGridColStartLocation(colStartLocation); // set column location
                    }

                    isValidShip = playerGrid.setShip(s); // checks if valid
                    if (!isValidShip) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Invalid Ship Placement");
                        alert.setContentText("Make sure ship doesn't go off the grid or overlap with other ships");
                        alert.showAndWait();
                        if(!result.isPresent()) {
                            mainApp.openView(new FXMLLoader(getClass().getResource("/view/Battleship.fxml")));
                        }

                    }
                }
            }
        }
        colorPlayerShips(); // colors player ships grey
    }

    /**
     * Will color all of the player's ships gray
     * If the grid as [i][j] has a ship part, it's node is colored gray
     */
    public void colorPlayerShips() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(playerGrid.getBox(i,j).hasShipPart()) {
                    playerNodes[i][j].setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
                }
            }
        }
    }

    /**
     * Used for testing
     */
    public void colorComputerShips() {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(computerGrid.getBox(i,j).hasShipPart()) {
                    computerNodes[i][j].setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
                }
            }
        }
    }

    /**
     * Will end the game if computer or player sinks all of opponent's ships
     */
    public void endGame() {
        Optional<ButtonType> o = new Alert(Alert.AlertType.CONFIRMATION, "Game over, " +
                (totalTimesPlayerHit < 17 ? "You Win!" : "You Lose!")).showAndWait();
        if (!o.isPresent()) {
            mainApp.openView(new FXMLLoader(getClass().getResource("/view/Battleship.fxml"))); // will return user to battleship menu
        }
        o.ifPresent(new Consumer<ButtonType>() {
            @Override
            public void accept(ButtonType buttonType) {
                if (buttonType.equals(ButtonType.OK)) {
                    mainApp.openView(new FXMLLoader(getClass().getResource("/view/Battleship.fxml"))); // will return user to battleship menu
                } else if (buttonType.equals(ButtonType.CANCEL)) {
                    mainApp.openView(new FXMLLoader(getClass().getResource("/view/MainMenu.fxml"))); // will return user to main menu
                }
            }
        });
    }

    /**
     * Will randomly place ships on a given grid
     * @param grid the grid we want to place ships on
     */
    public void randomlyPlaceShips(BattleshipGrid grid) {
        rand = new Random(); // used to randomly generate rotation, row, col
        int computerRowStart; // starting row
        int computerColStart; // starting col
        for(int i = 0; i < 5; i++) { // computer random ship adding
            boolean shipAdded = false;
            while(!shipAdded) {
                computerRowStart = rand.nextInt(9);
                computerColStart = rand.nextInt(9);
                Ship s = Ship.SHIPS[i];
                boolean isRotated = rand.nextBoolean();
                if(isRotated) { // if isRotated is true, rotate ship
                    s = s.shipRotate();
                }

                s.setGridRowStartLocation(computerRowStart); // set the row
                s.setGridColStartLocation(computerColStart); // set the col

                shipAdded = grid.setShip(s); // see if it goes of board or overlaps with other ships
            }
        }
    }
}