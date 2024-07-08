package game.sprites;

import biuoop.DrawSurface;
import game.Counter;
import game.interfaces.Sprite;

/**
 * Class for displaying the score.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;

    /**
     * Score indicator constructor.
     *
     * @param score score counter.
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.drawText(350, 15, "Score: " + Integer.toString(score.getValue()), 15);
    }

    @Override
    public void timePassed() {
        return;
    }

    @Override
    public String toString() {
        return Integer.toString(score.getValue());
    }
}
