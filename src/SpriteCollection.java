/**
 * Class containing a collection of sprites in the game.
 */
public class SpriteCollection {
    private java.util.ArrayList<Sprite> sprites = new java.util.ArrayList<Sprite>();
    private java.util.ArrayList<Sprite> removeQueue = new java.util.ArrayList<Sprite>();
    private java.util.ArrayList<Sprite> addQueue = new java.util.ArrayList<Sprite>();

    /**
     * Method to add sprite to the collection.
     * Adds the sprite to a queue to be added later (avoiding concurrent modification exceptions).
     *
     * @param s sprite to add.
     */
    public void addSprite(Sprite s) {
        addQueue.add(s);
    }

    /**
     * Method to remove sprite from the collection.
     * Adds the sprite to a queue to be removed later (avoiding concurrent modification exceptions).
     *
     * @param s sprite to be removed.
     */
    public void removeSprite(Sprite s) {
        removeQueue.add(s);
    }

    /**
     * Method to process the queues.
     */
    private void applyQueues() {
        for (Sprite s : removeQueue) {
            sprites.remove(s);
        }
        removeQueue.clear();
        sprites.addAll(addQueue);
        addQueue.clear();
    }

    /**
     * Method to notify all sprites that time has passed.
     */
    public void notifyAllTimePassed() {
        for (Sprite s : sprites) {
            s.timePassed();
        }
        applyQueues();
    }

    /**
     * Method to draw all sprites on a given surface.
     *
     * @param d surface to draw on.
     */
    public void drawAllOn(biuoop.DrawSurface d) {
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }
}
