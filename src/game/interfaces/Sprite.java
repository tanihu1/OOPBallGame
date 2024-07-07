package game.interfaces;

/**
 * Interface representing an object that can be drawn inside the game.
 */
public interface Sprite {
    /**
     * Method to draw the sprite on a given drawsurface.
     * @param d drawsurface to draw on.
     */
    void drawOn(biuoop.DrawSurface d);

    /**
     * Method to notify the sprite that time has passed.
     */
    void timePassed();
}
