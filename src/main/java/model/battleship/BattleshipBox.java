package model.battleship;

/**
 * Created by dylanbrisco on 2/27/17.
 */
public class BattleshipBox {
    int boxRowLocation;
    int boxColLocation;
    boolean hasShipPart;
    boolean isHit;
    boolean isMiss;

    public BattleshipBox(int boxRowLocation, int boxColLocation, boolean hasShipPart, boolean isHit, boolean isMiss) {
        this.boxRowLocation = boxRowLocation;
        this.boxColLocation = boxColLocation;
        this.hasShipPart = hasShipPart;
        this.isHit = isHit;
        this.isMiss = isMiss;
    }

    public boolean hasShipPart() {
        return hasShipPart;
    }

    public void setHasShipPart(boolean hasShipPart) {
        this.hasShipPart = hasShipPart;
    }

    public int getBoxRowLocation() {
        return boxRowLocation;
    }

    public void setBoxRowLocation(int boxRowLocation) {
        this.boxRowLocation = boxRowLocation;
    }

    public int getBoxColLocation() {
        return boxColLocation;
    }

    public void setBoxColLocation(int boxColLocation) {
        this.boxColLocation = boxColLocation;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public boolean isMiss() {
        return isMiss;
    }

    public void setMiss(boolean isMiss) {
        this.isMiss = isMiss;
    }
}