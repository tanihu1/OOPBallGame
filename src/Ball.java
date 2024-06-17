import biuoop.DrawSurface;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

/**
 * The class represents a ball object with a center, radius, color, and velocity.
 * It also handles the ball's interactions with defined borders and inverse borders.
 */
public class Ball implements Sprite {
    private Point center; //Ball center point
    private final int r; //Ball radius
    private java.awt.Color color; //Ball color
    private Velocity velocity = new Velocity(0, 0); //Ball velocity
    //Borders
    private final double cmp = 0.00001;
    private GameEnvironment g;

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
     * @param g     Game Environment reference.
     */
    public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment g) {
        this.center = new Point((double) x, (double) y);
        this.r = r;
        this.color = color;
        this.g = g;
    }

    /**
     * Ball constructor with double args.
     *
     * @param x     x coordinate of center.
     * @param y     y coordinate of center.
     * @param r     radius of ball.
     * @param color color of ball.
     * @param g     Game Environment reference.
     */
    public Ball(double x, double y, int r, java.awt.Color color, GameEnvironment g) {
        this.center = new Point((double) x, (double) y);
        this.r = r;
        this.color = color;
        this.g = g;
    }

    /**
     * Simple double comparing function.
     *
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
    public void timePassed(){
        moveOneStep();
    }
    public void moveOneStep() {
        Point futureLoc = new Point(this.getX() + this.getVelocity().getDx(),
                this.getY() + this.getVelocity().getDy());
        Line trajectory = new Line(this.center,futureLoc);
        CollisionInfo collision = g.getClosestCollision(trajectory);
        if(collision!=null){
            CollisionInfo collision2 = g.getClosestCollision(trajectory);
            switch (collision.getCollisionSide()) {
                case TOP:
                    futureLoc = new Point(this.getX(),collision.collisionPoint().getY() - this.getSize());
                    break;
                case BOTTOM:
                    futureLoc = new Point(this.getX(),collision.collisionPoint().getY() + this.getSize());
                    break;
                case LEFT:
                    futureLoc = new Point(collision.collisionPoint().getX() - this.getSize(),this.getY());
                    break;
                case RIGHT:
                    futureLoc = new Point(collision.collisionPoint().getX() + this.getSize(),this.getY());
                    break;
                default:
                    break;
            }
            this.center = futureLoc;
            this.setVelocity(collision.collisionObject().hit(collision.collisionPoint(),this.getVelocity()));
        }else{
            this.center = futureLoc;
        }
    }

    public void addToGame(Game game) {
        game.addSprite(this);
    }
}
