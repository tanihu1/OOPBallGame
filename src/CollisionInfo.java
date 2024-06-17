import java.util.ArrayList;
import java.util.Collections;

public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;
    public enum axis{
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }
    private axis collisionSide;
    //Constructor
    public CollisionInfo(Point collisionPoint, Collidable collisionObject){
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
        findCollisionSide();
    }
    private void findCollisionSide() {
        double xVal = collisionPoint.getX();
        double yVal = collisionPoint.getY();
        double top = collisionObject.getCollisionRectangle().getUpperLeft().getY();
        double bottom = top + collisionObject.getCollisionRectangle().getHeight();
        double left = collisionObject.getCollisionRectangle().getUpperLeft().getX();
        double right = left + collisionObject.getCollisionRectangle().getWidth();
        java.util.ArrayList<Double> distances = new ArrayList<>();
        distances.add(Math.abs(yVal-top));
        distances.add(Math.abs(yVal-bottom));
        distances.add(Math.abs(xVal-left));
        distances.add(Math.abs(xVal-right));
        int index = distances.indexOf(Collections.min((distances)));
        switch(index){
            case 0:
                collisionSide = axis.TOP;
                break;
            case 1:
                collisionSide = axis.BOTTOM;
                break;
            case 2:
                collisionSide = axis.LEFT;
                break;
            case 3:
                collisionSide = axis.RIGHT;
                break;
        }
    }
    // the point at which the collision occurs.
    public Point collisionPoint(){
        return collisionPoint;
    }
    // the collidable object involved in the collision.
    public Collidable collisionObject(){
        return collisionObject;
    }
    public axis getCollisionSide(){
        return collisionSide;
    }
}
