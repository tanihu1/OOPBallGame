package game.geometry;

import game.Game;
import game.collision.Velocity;

/**
 * A rectangle extension, will be used in the future to modify
 * hit method to remove the object from the screen one hit.
 */
public class Block extends Rectangle {
    private final Game g;

    /**
     * game.geometry.Block constructor.
     * @param upperLeft upperLeft point of the rectangle representing the block.
     * @param width width of said rectangle.
     * @param height height of said rectangle.
     * @param g game object reference.
     */
    public Block(Point upperLeft, double width, double height, Game g) {
        super(upperLeft, width, height);
        this.g = g;
    }

    /**
     * Overrides the hit method of the superclass to handle the collision between a point and a velocity.
     *
     * @param collisionPoint  the point where the collision occurs
     * @param currentVelocity the velocity of the object at the time of collision
     * @return the updated velocity after the collision
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        Velocity v = super.hit(collisionPoint, currentVelocity);
        g.removeSprite(this);
        g.removeCollidable(this);
        return v;
    }
}
