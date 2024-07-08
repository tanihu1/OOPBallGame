package game.interfaces;

/**
 * Interface for a hit notifier.
 */
public interface HitNotifier {
    /**
     * Add a hit listener to the listeners list.
     *
     * @param hl listener to add.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove a listener from the listeners list.
     *
     * @param hl listener to remove.
     */
    void removeHitListener(HitListener hl);
}
