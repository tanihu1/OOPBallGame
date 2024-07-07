package game.collision;

import game.geometry.Point;
import game.interfaces.Collidable;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class containing information about a collision between the ball and a
 * collidable object.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * Enum object representing the sides of the collision rectangle.
     */
    public enum Axis {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    private Axis collisionSide;

    /**
     * Constructor.
     *
     * @param collisionPoint  point of collision.
     * @param collisionObject object involved in the collision.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
        findCollisionSide();
    }

    /**
     * Method to find which side was collided with.
     */
    private void findCollisionSide() {
        double xVal = collisionPoint.getX();
        double yVal = collisionPoint.getY();
        double top = collisionObject.getCollisionRectangle().getUpperLeft().getY();
        double bottom = top + collisionObject.getCollisionRectangle().getHeight();
        double left = collisionObject.getCollisionRectangle().getUpperLeft().getX();
        double right = left + collisionObject.getCollisionRectangle().getWidth();
        java.util.ArrayList<Double> distances = new ArrayList<>();
        distances.add(Math.abs(yVal - top));
        distances.add(Math.abs(yVal - bottom));
        distances.add(Math.abs(xVal - left));
        distances.add(Math.abs(xVal - right));
        int index = distances.indexOf(Collections.min((distances)));
        switch (index) {
            case 0:
                collisionSide = Axis.TOP;
                break;
            case 1:
                collisionSide = Axis.BOTTOM;
                break;
            case 2:
                collisionSide = Axis.LEFT;
                break;
            case 3:
                collisionSide = Axis.RIGHT;
                break;
            default:
                break;
        }
    }

    /**
     * Returns the point of collision.
     *
     * @return the point of collision.
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /**
     * Returns the collidable object involved in the collision.
     *
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return collisionObject;
    }

    /**
     * Returns the side of the collidable object involved in the collision.
     *
     * @return the side of the collidable object involved in the collision.
     */
    public Axis getCollisionSide() {
        return collisionSide;
    }
}
