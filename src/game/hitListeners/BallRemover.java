package game.hitListeners;

import game.Counter;
import game.Game;
import game.geometry.Ball;
import game.geometry.Block;
import game.geometry.Rectangle;
import game.interfaces.HitListener;

public class BallRemover implements HitListener {
    private Game g;
    private Counter remainingBalls;
    public BallRemover(Game g,Counter remainingBalls) {
        this.g = g;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        return;
    }

    @Override
    public void hitEvent(Rectangle beingHit, Ball hitter) {
        hitter.removeFromGame(g);
        remainingBalls.decrease(1);
    }
}
