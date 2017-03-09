package model.battleship;

/**
 * Created by dylanbrisco on 2/27/17.
 */

public class Ship {
    public static Ship AIRCRAFT_CARRIER = new Ship(5,1); // 5 long ship
    public static Ship BATTLESHIP = new Ship(4,1); // 4 long ship
    public static Ship DESTROYER = new Ship(3,1); // 3 long ship
    public static Ship SUBMARINE = new Ship(3,1); // 3 long ship
    public static Ship PATROL_BOAT = new Ship(2,1); // 2 long ship
    public static Ship[] SHIPS = {AIRCRAFT_CARRIER, BATTLESHIP, DESTROYER, SUBMARINE, PATROL_BOAT};
    //Array list of ships and ship names
    public static String[] SHIPNAMES = {"Aircraft Carrier", "Battleship", "Destroyer", "Submarine", "Patrol Boat"};

    int gridRowStartLocation; // the starting row
    int gridRowEndLocation; // the ending row
    int gridColStartLocation;  // the starting column
    int gridColEndLocation; // the ending column
    int timesHit;

    /**
     * Default constructor for predefined ships
     * @param height
     * @param width
     */
    public Ship(int height, int width) {
        this.gridRowStartLocation = 0;
        this.gridRowEndLocation = height-1;
        this.gridColStartLocation = 0;
        this.gridColEndLocation = width-1;
    }

    /**
     * Constructor used in game
     * @param gridRowStartLocation starting row
     * @param gridRowEndLocation ending row
     * @param gridColStartLocation starting column
     * @param gridColEndLocation ending column
     * @param timesHits amount of times hit
     */
    public Ship(int gridRowStartLocation, int gridRowEndLocation, int gridColStartLocation, int gridColEndLocation, int timesHits) {
        this.gridRowStartLocation = gridRowStartLocation;
        this.gridRowEndLocation = gridRowEndLocation;
        this.gridColStartLocation = gridColStartLocation;
        this.gridColEndLocation = gridColEndLocation;
        this.timesHit = timesHits;
    }

    /**
     * Returns the ending location of the row
     * @return gridRowEndLocation
     */
    public int getGridRowEndLocation() {
        return gridRowEndLocation;
    }

    /**
     * Returns the starting location of the row
     * @return gridRowStartLocation
     */
    public int getGridRowStartLocation() {
        return gridRowStartLocation;
    }

    /**
     * Sets the gridRowStartingLocation
     * @param gridRowStartLocation the gridRowStartingLocation
     */
    public void setGridRowStartLocation(int gridRowStartLocation) {
        this.gridRowEndLocation += gridRowStartLocation - this.gridRowStartLocation;
        this.gridRowStartLocation = gridRowStartLocation;
    }

    /**
     * Returns the starting column
     * @return gridColStartLocation
     */
    public int getGridColStartLocation() {
        return gridColStartLocation;
    }

    /**
     * Sets the starting column
     * @param gridColStartLocation the starting column
     */
    public void setGridColStartLocation(int gridColStartLocation) {
        this.gridColEndLocation += gridColStartLocation - this.gridColStartLocation;
        this.gridColStartLocation = gridColStartLocation;
    }

    /**
     * Returns the ending column location
     * @return gridColEndLocation
     */
    public int getGridColEndLocation() {
        return gridColEndLocation;
    }

    /**
     * Rotates a ship by reversing row and columns
     * @return a rotated ship
     */
    public Ship shipRotate() {
        return new Ship(this.getGridColStartLocation(), this.getGridColEndLocation(), this.getGridRowStartLocation(), this.getGridRowEndLocation(), 0);
    }
}
