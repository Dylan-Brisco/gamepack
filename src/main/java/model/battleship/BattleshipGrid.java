package model.battleship;

/**
 * Created by dylanbrisco on 2/27/17.
 */
public class BattleshipGrid {
    // Grid can be represented as 2D array of boxes
    private BattleshipBox[][] battleShipGrid;

    public BattleshipGrid() {
        battleShipGrid = new BattleshipBox[9][9]; // grid is 9 by 9
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                battleShipGrid[i][j] = new BattleshipBox(i,j,false,false,false); // fills grid with boxes
            }
        }
    }

    /**
     * Returns a box at given location
     * @param row row index of box
     * @param col column index of box
     * @return box at row col
     */
    public BattleshipBox getBox(int row, int col) {
        return battleShipGrid[row][col];
    }

    /**
     * Will set a ship to a location and return true if it did
     * @param s the ship we are setting
     * @return true if placed ship, false if ship was invalid and not placed
     */
    public boolean setShip(Ship s) {

        if(s.getGridColStartLocation() < 0 || s.getGridColEndLocation() > 8
                || s.getGridRowStartLocation() < 0 || s.getGridRowEndLocation() > 8 ) {
            return false; // the ship goes off the board, return false
        }
        for(int i = s.getGridRowStartLocation(); i <= s.getGridRowEndLocation(); i++) {
            for (int j = s.getGridColStartLocation(); j <= s.getGridColEndLocation(); j++) {
                if (battleShipGrid[i][j].hasShipPart()) {
                    return false; // if there is already a ship in one of the boxes, return false
                }
            }
        }

        for(int i = s.getGridRowStartLocation(); i <= s.getGridRowEndLocation(); i++) {
            for (int j = s.getGridColStartLocation(); j <= s.getGridColEndLocation(); j++) {
                battleShipGrid[i][j].setHasShipPart(true); // set all the boxes containing the ship to hasShipPart(true)
            }
        }
        return true; // ship was placed
    }

    /**
     * Will attempt to make a shot at a certain row,col location
     * If it hit, return true
     * If miss, return false
     * @param row the row location
     * @param col the column location
     * @return true if hit, false if miss
     */
    public boolean takeShot(int row, int col) {
        if(battleShipGrid[row][col].hasShipPart()) { // if row,col has a ship part, it was a hit
            battleShipGrid[row][col].setHit(true);
        }
        else { // if row, col doesn't have a ship part it was a miss
            battleShipGrid[row][col].setMiss(true);
        }
        return battleShipGrid[row][col].isHit(); // true if hit, false if miss
    }
}
