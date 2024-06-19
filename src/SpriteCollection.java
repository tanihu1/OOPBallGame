public class SpriteCollection {
    java.util.ArrayList<Sprite> sprites = new java.util.ArrayList<Sprite>();
    java.util.ArrayList<Sprite> removeQueue = new java.util.ArrayList<Sprite>();
    java.util.ArrayList<Sprite> addQueue = new java.util.ArrayList<Sprite>();

    public void addSprite(Sprite s) {
        addQueue.add(s);
    }
    public void removeSprite(Sprite s) {
        removeQueue.add(s);
    }
    private void applyQueues(){
        for(Sprite s:removeQueue){
            sprites.remove(s);
        }
        removeQueue.clear();
        sprites.addAll(addQueue);
        addQueue.clear();
    }
    // call timePassed() on all sprites.
    public void notifyAllTimePassed(){
        for (Sprite s : sprites) {
            s.timePassed();
        }
        applyQueues();
    }
    // call drawOn(d) on all sprites.
    public void drawAllOn(biuoop.DrawSurface d){
        for(Sprite s : sprites){
            s.drawOn(d);
        }
    }
}
