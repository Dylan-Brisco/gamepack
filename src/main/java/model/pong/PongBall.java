package model.pong;

/**
 * Ball has an x location, y location, x velocity, y velocity, and radius
 */
public class PongBall {
    private double x;
    private double y;
    private double velocityX;
    private double velocityY;
    public static final double RADIUS = 3;

    /**
     * PongBall constructor
     * @param x x location of ball
     * @param y y location of ball
     * @param velocityX speed in x direction
     * @param velocityY speed in y direction
     */
    public PongBall(double x, double y, double velocityX, double velocityY) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    /**
     * Returns current x location of ball
     * @return x location of ball
     */
    public double getX() {
        return x;
    }

    /**
     * Sets x location of ball
     * @param x x location
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the y location of the ball
     * @return y location of the ball
     */
    public double getY() {
        return y;
    }

    /**
     * Sets y location of ball
     * @param y y location
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns current x velocity of the ball
     * @return x velocity of ball
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * Sets the x velocity of the ball
     * @param velocityX x velocity of the ball
     */
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    /**
     * Returns the y velocity of the ball
     * @return y velocity of the ball
     */
    public double getVelocityY() {
        return velocityY;
    }

    /**
     * Sets the y velocity of the ball
     * @param velocityY y velocity of the ball
     */
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
}
