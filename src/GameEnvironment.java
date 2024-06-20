import java.util.ArrayList;

/**
 * Class to store all objects relevant to the game.
 */
public class GameEnvironment {
    private final java.util.List<Collidable> collidables = new ArrayList<>();
    private final java.util.List<Collidable> removeQueue = new ArrayList<>();

    /**
     * Method to add a collidable to the game environment.
     *
     * @param c collidable to add.
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }
    /**
     * Method to remove a collidable from the game environment.
     * Adds collidable to remove queue to avoid ConcurrentModificationException.
     *
     * @param c collidable to remove.
     */
    public void addToRemoveQueue(Collidable c) {
        removeQueue.add(c);
    }

    /**
     * Removes all collidables from the remove queue and clears the queue.
     */
    private void removeCollidables() {
        for (Collidable c : removeQueue) {
            collidables.remove(c);
        }
        removeQueue.clear();
    }

    /**
     * Tests a given trajectory for collision with the game collidables.
     *
     * @param trajectory the trajectory to test.
     * @return null if no collision, otherwise the collision info.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        double currentDistance = Double.POSITIVE_INFINITY;
        Point currentCollision = null;
        Collidable currentObject = null;
        for (Collidable c : collidables) {
            Point collision = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (collision == null) {
                continue;
            }
            if (trajectory.start().distance(collision) < currentDistance) {
                currentCollision = collision;
                currentObject = c;
            }
        }
        if (currentCollision != null) {
            return new CollisionInfo(currentCollision, currentObject);
        }
        return null;
    }
}
