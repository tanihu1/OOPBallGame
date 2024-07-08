package game.geometry;

import biuoop.DrawSurface;
import game.Game;
import game.interfaces.Collidable;
import game.collision.Velocity;
import game.interfaces.HitListener;
import game.interfaces.HitNotifier;
import game.interfaces.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Basic rectangle class.
 */
public class Rectangle implements Collidable, Sprite, HitNotifier {
    private final Point upperLeft;
    private final Point bottomRight;
    private final double width;
    private final double height;
    private final double cmp = 0.00001;
    private Color color;
    private List<HitListener> hitListeners = new ArrayList<>();

    /**
     * game.geometry.Rectangle constructor.
     *
     * @param upperLeft upper left point of the rectangle.
     * @param width     width of the rectangle.
     * @param height    height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.bottomRight = new Point(upperLeft.getX() + width,
                upperLeft.getY() + height);
        this.width = width;
        this.height = height;
    }

    /**
     * Constructor using two points.
     *
     * @param upperLeft   upper left point.
     * @param bottomRight bottom right point.
     */
    public Rectangle(Point upperLeft, Point bottomRight) {
        this.upperLeft = upperLeft;
        this.bottomRight = bottomRight;
        this.width = Math.abs(bottomRight.getX() - upperLeft.getX());
        this.height = Math.abs(bottomRight.getY() - upperLeft.getY());
    }

    /**
     * Sets the color of the rectangle.
     *
     * @param color the new color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Creates a list of intersection points with the given line.
     *
     * @param line the line to test intersections with.
     * @return a list of intersection points.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> result = new ArrayList<>();
        for (Line recLine : this.rectangleToLines()) {
            if (recLine.intersectionWith(line) != null) {
                result.add(recLine.intersectionWith(line));
            }
        }
        return result;
    }

    /**
     * Method to compare two floating point numbers.
     *
     * @param x first value to compare.
     * @param y second value to compare.
     * @return true if the values equal.
     */
    boolean dCmp(double x, double y) {
        return Math.abs(x - y) <= cmp;
    }

    /**
     * Creates a list of lines that make up the rectangle.
     *
     * @return a list of lines that make up the rectangle.
     */
    public java.util.List<Line> rectangleToLines() {
        java.util.List<Line> lineList = new ArrayList<>();
        lineList.add(new Line(upperLeft.getX(),
                upperLeft.getY(),
                upperLeft.getX(),
                upperLeft.getY() + height));
        lineList.add(new Line(upperLeft.getX(),
                upperLeft.getY(),
                upperLeft.getX() + width,
                upperLeft.getY()));
        lineList.add(new Line(upperLeft.getX() + width,
                upperLeft.getY(),
                upperLeft.getX() + width,
                upperLeft.getY() + height));
        lineList.add(new Line(upperLeft.getX(),
                upperLeft.getY() + height,
                upperLeft.getX() + width,
                upperLeft.getY() + height));
        return lineList;
    }

    /**
     * game.geometry.Rectangle width getter.
     *
     * @return the width value of the rectangle.
     */
    public double getWidth() {
        return width;
    }

    /**
     * game.geometry.Rectangle height getter.
     *
     * @return the height value of the rectangle.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Upper left point getter.
     *
     * @return the upper left point of the rectangle.
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Bottom right point getter.
     *
     * @return the bottom right point of the rectangle.
     */
    public Point getBottomRight() {
        return bottomRight;
    }

    /**
     * Returns the collision rectangle.
     *
     * @return the collision rectangle.
     */
    public Rectangle getCollisionRectangle() {
        return this;
    }

    /**
     * Calculates and returns the new velocity of the ball after it hits the rectangle.
     *
     * @param collisionPoint  the point where the collision occurred
     * @param currentVelocity the current velocity of the object
     * @param hitter ball that hit the rectangle.
     * @return updated velocity after the hit.
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball hitter) {
        for (HitListener hl : hitListeners) {
            hl.hitEvent(this, hitter);
        }
        //game.collision.Velocity offset to make the ball movement a bit random and more natural
        java.util.Random rand = new java.util.Random();
        double vOffset = -rand.nextDouble(); //On unix bound must not be defined inside func.
        int left = 0;
        int right = 2;
        //Finding collision side
        ArrayList<Double> distanceFromSides = new ArrayList<>();
        distanceFromSides.add(Math.abs(collisionPoint.getX() - upperLeft.getX()));
        distanceFromSides.add(Math.abs(collisionPoint.getY() - upperLeft.getY()));
        distanceFromSides.add(Math.abs(collisionPoint.getX() - bottomRight.getX()));
        distanceFromSides.add(Math.abs(collisionPoint.getY() - bottomRight.getY()));
        double min = Collections.min(distanceFromSides);
        if (dCmp(min, distanceFromSides.get(left)) || dCmp(min, distanceFromSides.get(right))) {
            currentVelocity.changeDirection(-currentVelocity.getDx(), currentVelocity.getDy());
            return currentVelocity;
        }
        currentVelocity.changeDirection(currentVelocity.getDx(), -currentVelocity.getDy());
        return currentVelocity;
    }

    /**
     * A method to draw the ball on the given DrawSurface.
     *
     * @param surface the DrawSurface to draw the ball on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillRectangle((int) upperLeft.getX(),
                (int) upperLeft.getY(),
                (int) width,
                (int) height);
    }

    /**
     * Method to notify the object that time has passed.
     */
    public void timePassed() {
        return;
    }

    /**
     * Adds object to the game.
     *
     * @param g game reference to add the object to.
     */
    public void addToGame(Game g) {
        g.addCollidable(this.getCollisionRectangle());
        g.addSprite(this.getCollisionRectangle());
    }

    /**
     * Color getter.
     *
     * @return the color of the rectangle.
     */
    public Color getColor() {
        return color;
    }

    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }
}
