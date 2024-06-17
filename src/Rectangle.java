import biuoop.DrawSurface;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Rectangle implements Collidable, Sprite{
    private final Point upperLeft;
    private final Point bottomRight;
    private final double width;
    private final double height;
    private final double cmp = 0.00001;
    private Color color;

    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.bottomRight = new Point(upperLeft.getX() + width,
                upperLeft.getY() + height);
        this.width = width;
        this.height = height;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> result = new ArrayList<>();
        for (Line recLine : this.rectangleToLines()) {
            if (recLine.intersectionWith(line) != null) {
                result.add(recLine.intersectionWith(line));
            }
        }
        return result;
    }

    boolean dCmp(double x, double y) {
        return Math.abs(x - y) <= cmp;
    }

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

    // Return the width and height of the rectangle
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    // Returns the upper-left point of the rectangle.
    public Point getUpperLeft() {
        return upperLeft;
    }

    public Point getBottomRight(){
        return bottomRight;
    }
    public Rectangle getCollisionRectangle() {
        return this;
    }

    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        //Velocity offset to make the ball movement a bit random and more natural
        java.util.Random rand = new java.util.Random();
        //TODO: Test if voffset is needed
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
        if (dCmp(min, distanceFromSides.get(left)) ||
                dCmp(min, distanceFromSides.get(right))) {
            currentVelocity.changeDirection(-currentVelocity.getDx(),currentVelocity.getDy());
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
    public void timePassed(){
        return;
    }
    public void addToGame(Game g){
        g.addCollidable(this);
        g.addSprite(this);
    }

    public Color getColor() {
        return color;
    }
}
