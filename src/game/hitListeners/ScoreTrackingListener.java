package game.hitListeners;

import game.Counter;
import game.geometry.Ball;
import game.geometry.Block;
import game.geometry.Rectangle;
import game.interfaces.HitListener;

/**
 * Class for a listener to increase the player's score.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter score;
    private final int scorePerBlock = 5;

    /**
     * The listener's constructor.
     *
     * @param score counter keeping track of the player's score.
     */
    public ScoreTrackingListener(Counter score) {
        this.score = score;
    }

    /**
     * When a block is blown by the player, increase the score.
     *
     * @param beingHit block being hit.
     * @param hitter   ball hitting the block.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        score.increase(scorePerBlock);
    }

    /**
     * Do nothing on border hits.
     *
     * @param beingHit rectangle being hit.
     * @param hitter   ball hitting the rectangle.
     */
    @Override
    public void hitEvent(Rectangle beingHit, Ball hitter) {
        return;
    }
}
