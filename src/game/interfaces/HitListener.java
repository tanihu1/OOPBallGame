package game.interfaces;

import game.geometry.Ball;
import game.geometry.Block;
import game.geometry.Rectangle;

public interface HitListener {
    // This method is called whenever the beingHit object is hit.
    // The hitter parameter is the Ball that's doing the hitting.
    void hitEvent(Block beingHit, Ball hitter);
    void hitEvent(Rectangle beingHit, Ball hitter);
}
