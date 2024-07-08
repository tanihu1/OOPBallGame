package game.interfaces;

import game.geometry.Ball;
import game.geometry.Block;
import game.geometry.Rectangle;

/**
 * Interface for a hit listener.
 */
public interface HitListener {
    /**
     * What to do when a block is hit.
     *
     * @param beingHit block hit.
     * @param hitter   ball hitting the block.
     */
    void hitEvent(Block beingHit, Ball hitter);

    /**
     * What to do when a rectangle is hit.
     *
     * @param beingHit rectangle hit.
     * @param hitter   ball hitting the rectangle.
     */
    void hitEvent(Rectangle beingHit, Ball hitter);
}
