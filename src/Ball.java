import biuoop.DrawSurface;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

/**
 * The class represents a ball object with a center, radius, color, and velocity.
 * It also handles the ball's interactions with defined borders and inverse borders.
 */
public class Ball {
    private Point center; //Ball center point
    private final int r; //Ball radius
    private java.awt.Color color; //Ball color
    private Velocity velocity = new Velocity(0, 0); //Ball velocity
    //Borders
    private Border[] borders = new Border[0]; //Ball border list
    private int numBorders = 0;
    private Border[] inverseBorders = new Border[0]; //Ball inverse border list
    private int numInverseBorders = 0;
    private final double cmp = 0.00001;

    /**
     * Ball constructor with point arg for center.
     *
     * @param center center point of ball.
     * @param r      radius of ball.
     * @param color  color of ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
    }

    /**
     * Ball constructor with  int args.
     *
     * @param x     x coordinate of center.
     * @param y     y coordinate of center.
     * @param r     radius of ball.
     * @param color color of ball.
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        this.center = new Point((double) x, (double) y);
        this.r = r;
        this.color = color;
    }

    /**
     * Ball constructor with double args.
     *
     * @param x     x coordinate of center.
     * @param y     y coordinate of center.
     * @param r     radius of ball.
     * @param color color of ball.
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new Point((double) x, (double) y);
        this.r = r;
        this.color = color;
    }

    /**
     * Simple double comparing function.
     * @param x first double.
     * @param y second double.
     * @return true if doubles are equal up to 5 digits.
     */
    private boolean dCmp(double x, double y) {
        return Math.abs(x - y) <= cmp;
    }
    // accessors

    /**
     * A method to get the x-coordinate of the center of the Ball.
     *
     * @return The x-coordinate of the center as an integer.
     */
    public int getX() {
        return (int) center.getX();
    }

    /**
     * A method to get the Y coordinate of the Ball's center.
     *
     * @return the Y coordinate of the center
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * A method to get the size of the Ball.
     *
     * @return the size of the Ball as an integer
     */
    public int getSize() {
        return r;
    }

    /**
     * A method to get the color of the ball.
     *
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return color;
    }

    /**
     * A method to draw the ball on the given DrawSurface.
     *
     * @param surface the DrawSurface to draw the ball on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillCircle((int) center.getX(), (int) center.getY(), r);
    }

    /**
     * Set a random color for the ball.
     */
    public void setRandomColor() {
        java.util.Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        color = new Color(r, g, b);
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v the new velocity to be set
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the velocity of the ball to the specified values.
     *
     * @param dx the change in x-coordinate of the velocity
     * @param dy the change in y-coordinate of the velocity
     */
    public void setVelocity(double dx, double dy) {
        velocity = new Velocity(dx, dy);
    }

    /**
     * A method to get the velocity of the ball.
     *
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return velocity;
    }
    //BORDER LOGIC

    /**
     * A method to add a border to the ball.
     *
     * @param x the starting point of the border
     * @param y the ending point of the border
     */
    public void addBorder(Point x, Point y) {
        //Compensating diameter
        x = new Point(x.getX() + 2 * r, x.getY() + 2 * r);
        y = new Point(y.getX() - 2 * r, y.getY() - 2 * r);
        borders = Arrays.copyOf(borders, numBorders + 1);
        borders[numBorders++] = new Border(x, y);
    }

    /**
     * A method to add an inverse border to the Ball object.
     * Inverse borders are the opposite of regular borders.
     *
     * @param x the starting point of the inverse border
     * @param y the ending point of the inverse border
     */
    public void addInverseBorder(Point x, Point y) {
        //Compensating diameter
        x = new Point(x.getX() - 2 * r, x.getY() - 2 * r);
        y = new Point(y.getX() + 2 * r, y.getY() + 2 * r);
        inverseBorders = Arrays.copyOf(inverseBorders, numInverseBorders + 1);
        inverseBorders[numInverseBorders++] = new Border(x, y);
    }

    /**
     * A method to check if a given point is inside a specified border.
     *
     * @param b   the border to check against
     * @param loc the location (point) to check
     * @return true if the point is inside the border, false otherwise
     */
    private boolean pointInsideBorder(Border b, Point loc) {
        return (loc.getX() > b.getStart().getX()
                && loc.getX() < b.getEnd().getX()
                && loc.getY() > b.getStart().getY()
                && loc.getY() < b.getEnd().getY());
    }

    /**
     * A function to move the ball one step, handling collisions with borders.
     */
    public void moveOneStep() {
        //Velocity offset to make the ball movement a bit random and more natural
        java.util.Random rand = new java.util.Random();
        double vOffset = -rand.nextDouble(); //On unix bound must not be defined inside func.
        center = velocity.applyToPoint(center);
        //Iterating over each registered border and test for collision
        for (Border border : borders) {
            //If a collision is detected, we change the location and reverse the velocity
            if (center.getX() < border.getStart().getX()) {
                center.changeX(border.getStart().getX());
                velocity.changeDirection(vOffset * velocity.getDx(), velocity.getDy());
            } else if (center.getX() > border.getEnd().getX()) {
                center.changeX(border.getEnd().getX());
                velocity.changeDirection(vOffset * velocity.getDx(), velocity.getDy());
            }
            if (center.getY() < border.getStart().getY()) {
                center.changeY(border.getStart().getY());
                velocity.changeDirection(velocity.getDx(), vOffset * velocity.getDy());
            } else if (center.getY() > border.getEnd().getY()) {
                center.changeY(border.getEnd().getY());
                velocity.changeDirection(velocity.getDx(), vOffset * velocity.getDy());
            }
        }
        //Iterating over each inverse border and applying the opposite logic
        final int leftX = 0;
        final int topY = 1;
        final int rightX = 2;
        final int bottomY = 3;
        for (Border border : inverseBorders) {
            if (pointInsideBorder(border, center)) {
                //Axis represent The axis of the border hit by the ball
                int axis = border.closetAxis(center);
                switch (axis) {
                    case leftX:
                        center.changeX(border.getStart().getX());
                        velocity.changeDirection(vOffset * velocity.getDx(), velocity.getDy());
                        break;
                    case topY:
                        center.changeY(border.getStart().getY());
                        velocity.changeDirection(velocity.getDx(), vOffset * velocity.getDy());
                        break;
                    case rightX:
                        center.changeX(border.getEnd().getX());
                        velocity.changeDirection(vOffset * velocity.getDx(), velocity.getDy());
                        break;
                    case bottomY:
                        center.changeY(border.getEnd().getY());
                        velocity.changeDirection(velocity.getDx(), vOffset * velocity.getDy());
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
