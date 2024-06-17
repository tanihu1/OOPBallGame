import java.util.ArrayList;

public class GameEnvironment {
    private final java.util.List<Collidable> collidables = new ArrayList<>();
    // add the given collidable to the environment.
    public void addCollidable(Collidable c){
        collidables.add(c);
    }
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }
    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.
    public CollisionInfo getClosestCollision(Line trajectory){
        double currentDistance = Double.POSITIVE_INFINITY;
        Point currentCollision = null;
        Collidable currentObject = null;
        for(Collidable c:collidables){
            Point collision = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if(collision==null) continue;
            if(trajectory.start().distance(collision)<currentDistance){
                currentCollision = collision;
                currentObject = c;
            }
        }
        if(currentCollision!=null){
            return new CollisionInfo(currentCollision,currentObject);
        }
        return null;
    }
}
