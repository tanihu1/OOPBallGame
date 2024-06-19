public class Block extends Rectangle {
    private final Game g;
    public Block(Point upperLeft, double width, double height, Game g) {
        super(upperLeft, width, height);
        this.g=g;
    }
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        Velocity v = super.hit(collisionPoint, currentVelocity);
        return v;
    }
}
