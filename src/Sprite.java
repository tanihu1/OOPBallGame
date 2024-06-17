public interface Sprite {
    // draw the sprite to the screen
    void drawOn(biuoop.DrawSurface d);
    // notify the sprite that time has passed
    void timePassed();
}
