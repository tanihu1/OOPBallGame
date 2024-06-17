public class SpriteCollection {
    java.util.ArrayList<Sprite> sprites = new java.util.ArrayList<Sprite>();

    public void addSprite(Sprite s) {
        sprites.add(s);
    }
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }
    // call timePassed() on all sprites.
    public void notifyAllTimePassed(){
        for (Sprite s : sprites) {
            s.timePassed();
        }
    }
    // call drawOn(d) on all sprites.
    public void drawAllOn(biuoop.DrawSurface d){
        for(Sprite s : sprites){
            s.drawOn(d);
        }
    }
}
