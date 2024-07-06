package game.collision;

import game.geometry.Point;

/**
 * A velocity class that tracks the movement speed and direction of a ball.
 */
public class Velocity {
    private double dx;
    private double dy;
    private final double cmp = 0.00001;

    /**
     * game.collision.Velocity constructor using two doubles.
     *
     * @param dx x movement.
     * @param dy y movement.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
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

    /**
     * A static method that creates a new game.collision.Velocity object based on the given angle and speed.
     *
     * @param angle the angle in radians
     * @param speed the speed of the velocity
     * @return a new game.collision.Velocity object with the calculated dx and dy components
     */
    public Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.cos(angle);
        double dy = speed * Math.sin(angle);
        return new Velocity(dx, dy);
    }

    /**
     * Change the angle of the velocity.
     *
     * @param angle angle to change to.
     */
    public void changeAngle(int angle) {
        double radians = Math.toRadians(angle);
        double cosTheta = Math.cos(radians);
        double sinTheta = Math.sin(radians);
        double[] newVelocity = applyRotation(dx, dy, radians);
        this.dx = -newVelocity[0] * cosTheta + newVelocity[1] * sinTheta;
        this.dy = newVelocity[0] * sinTheta - newVelocity[1] * cosTheta;
    }

    /**
     * Calculate the angle between the old and new direction.
     *
     * @param newDx the new x-direction.
     * @param newDy the new y-direction.
     */
    public void changeDirection(double newDx, double newDy) {
        // Calculate the angle between the old and new direction
        double oldAngle = Math.atan2(dy, dx);
        double newAngle = Math.atan2(newDy, newDx);

        // Calculate the difference in angles
        double angleDiff = newAngle - oldAngle;

        // Apply the rotation matrix to the velocity vector
        double[] rotatedVector = applyRotation(dx, dy, angleDiff);

        // Update the velocity components
        this.dx = rotatedVector[0];
        this.dy = rotatedVector[1];
    }

    /**
     * A method that sets the velocity components dx and dy.
     *
     * @param dx the new value for the x-component of velocity
     * @param dy the new value for the y-component of velocity
     */
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Apply rotation matrix to the given coordinates (x, y) based on the specified angle.
     *
     * @param x     the x-coordinate
     * @param y     the y-coordinate
     * @param angle the angle of rotation in radians
     * @return an array containing the transformed coordinates after rotation
     */
    private double[] applyRotation(double x, double y, double angle) {
        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);
        return new double[]{x * cosTheta - y * sinTheta, x * sinTheta + y * cosTheta};
    }

    /**
     * Dx accessor.
     *
     * @return dx value.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Dy accessor.
     *
     * @return dy value.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Takes a point and applies the current velocity components to return a new point.
     *
     * @param p the point to which the velocity components are applied
     * @return a new point with the updated position based on the current velocity
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }
}
