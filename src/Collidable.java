/**
 * Collidable interface representing objects that can be
 * collided with.
 */
public interface Collidable {
    /**
     * Get the collision rectangle for this object.
     *
     * @return the collision rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * Calculates the velocity after a collision at the given point, based on the current velocity.
     *
     * @param collisionPoint  the point where the collision occurred
     * @param currentVelocity the current velocity of the object
     * @return the velocity after the collision
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity);
}