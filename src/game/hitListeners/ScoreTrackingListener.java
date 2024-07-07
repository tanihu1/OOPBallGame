package game.hitListeners;

import game.Counter;
import game.Game;
import game.geometry.Ball;
import game.geometry.Block;
import game.geometry.Rectangle;
import game.interfaces.HitListener;

public class ScoreTrackingListener implements HitListener {
    private Counter score;
    private final int SCORE_PER_BLOCK = 5;
    public ScoreTrackingListener(Counter score) {
        this.score = score;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        score.increase(SCORE_PER_BLOCK);
    }

    @Override
    public void hitEvent(Rectangle beingHit, Ball hitter) {
        return;
    }
}
