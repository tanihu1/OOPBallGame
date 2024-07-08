package game.hitListeners;

import game.Counter;
import game.Game;
import game.geometry.Ball;
import game.geometry.Block;
import game.geometry.Rectangle;
import game.interfaces.HitListener;

/**
 * Class for hit listener responsible for removing balls.
 */
public class BallRemover implements HitListener {
    private Game g;
    private Counter remainingBalls;

    /**
     * BallRemover constructor.
     * @param g game reference.
     * @param remainingBalls remaining balls counter.
     */
    public BallRemover(Game g, Counter remainingBalls) {
        this.g = g;
        this.remainingBalls = remainingBalls;
    }

    /**
     * If a block is being hit, a ball isn't removed.
     * @param beingHit block being hit.
     * @param hitter ball hitting the block.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        return;
    }

    /**
     * If the death-zone rectangle is hit, removes the ball.
     * @param beingHit rectangle being hit.
     * @param hitter ball hitting the rectangle.
     */
    @Override
    public void hitEvent(Rectangle beingHit, Ball hitter) {
        hitter.removeFromGame(g);
        remainingBalls.decrease(1);
    }
}
