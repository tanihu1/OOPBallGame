package game.hitListeners;

import game.Counter;
import game.Game;
import game.geometry.Ball;
import game.geometry.Block;
import game.geometry.Rectangle;
import game.interfaces.HitListener;

public class BlockRemover implements HitListener {
    private Game g;
    private Counter remainingBlocks;
    public BlockRemover(Game g,Counter remainingBlocks) {
        this.g = g;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(g);
        remainingBlocks.decrease(1);
    }

    @Override
    public void hitEvent(Rectangle beingHit, Ball hitter) {
        return;
    }
}
