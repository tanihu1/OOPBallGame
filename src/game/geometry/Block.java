package game.geometry;

import game.Game;
import game.collision.Velocity;
import game.interfaces.HitListener;
import game.interfaces.HitNotifier;

import java.util.ArrayList;
import java.util.List;

/**
 * A rectangle extension, will be used in the future to modify
 * hit method to remove the object from the screen one hit.
 */
public class Block extends Rectangle implements HitNotifier {
    private final Game g;
    private List<HitListener> hitListeners = new ArrayList<>();

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
    public Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball hitter) {
        Velocity v = super.hit(collisionPoint, currentVelocity,hitter);
        if(!ballColorMatch(hitter)) {
            notifyHit(hitter);
        }
        return v;
    }

    public Boolean ballColorMatch(Ball ball) {
        return ball.getColor().equals(super.getColor());
    }

    public void removeFromGame(Game g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}
