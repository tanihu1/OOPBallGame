package game.hitListeners;

import game.Counter;
import game.Game;
import game.geometry.Ball;
import game.geometry.Block;
import game.geometry.Rectangle;
import game.interfaces.HitListener;

/**
 * Hit listener responsible for removing blocks hit by the balls.
 */
public class BlockRemover implements HitListener {
    private Game g;
    private Counter remainingBlocks;

    /**
     * Listener constructor.
     *
     * @param g               game reference.
     * @param remainingBlocks remaining blocks counter.
     */
    public BlockRemover(Game g, Counter remainingBlocks) {
        this.g = g;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * If the ball hitting the block matches the block in color, listener will not be notified.
     * Else, set the ball color to match the block and remove the block.
     *
     * @param beingHit block being hit.
     * @param hitter   ball hitting the block.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.setColor(beingHit.getColor());
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(g);
        remainingBlocks.decrease(1);
    }

    /**
     * Rectangles are not removed when hit by the ball.
     *
     * @param beingHit rectangle being hit.
     * @param hitter   ball hitting the rectangle.
     */
    @Override
    public void hitEvent(Rectangle beingHit, Ball hitter) {
        return;
    }
}
