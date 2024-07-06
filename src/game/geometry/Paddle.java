package game.geometry;

import biuoop.DrawSurface;
import biuoop.GUI;
import game.Game;
import game.collision.Collidable;
import game.collision.Velocity;
import game.sprites.Sprite;

import java.awt.Color;

/**
 * game.geometry.Paddle class for the game.
 */
public class Paddle implements Collidable, Sprite {
    private final biuoop.KeyboardSensor keyboard;
    private Rectangle collisionObject;
    private final double cmp = 0.00001;
    private int moveSpeed;
    private int width;
    private int height;
    private Game g;
    private boolean isInGame = false;
    //TODO check if needed
    private boolean canMove = true;
    //The shadow paddle will create the illusion of teleporting paddle.
    private static boolean shadowPaddleInitialized = false;
    private Paddle shadowPaddle = null;

    /**
     * game.geometry.Paddle constructor.
     *
     * @param gui       reference to the GUI.
     * @param g         reference to the game environment.
     * @param moveSpeed the speed of the paddle.
     * @param width     the width of the paddle.
     * @param height    the height of the paddle.
     */
    public Paddle(GUI gui, Game g, int moveSpeed, int width, int height) {
        this.keyboard = gui.getKeyboardSensor();
        this.moveSpeed = moveSpeed;
        this.width = width;
        this.height = height;
        this.g = g;
        collisionObject = new Rectangle(new Point(300, 560), width, height);
        collisionObject.setColor(Color.ORANGE);
        if(shadowPaddleInitialized==false) {
            shadowPaddleInitialized = true;
            shadowPaddle = new Paddle(gui,g,moveSpeed,width,height);
            shadowPaddle.collisionObject =new Rectangle(new Point(300, 560), width, height);
            shadowPaddle.collisionObject.setColor(Color.ORANGE);
        }
    }

    /**
     * A method to compare floating point numbers.
     *
     * @param x first value to compare.
     * @param y second value to compare.
     * @return true if values are equal up to 5 digits.
     */
    private boolean dCmp(double x, double y) {
        return Math.abs(x - y) <= cmp;
    }

    /**
     * Moves the paddle right. If the paddle is at the right edge of the screen, it teleports
     * to the left edge.
     */
    public void moveRight() {
        Rectangle updatedCollisionObject;
        Point updatedUpperLeft = new Point(collisionObject.getUpperLeft().getX() + moveSpeed,
                collisionObject.getUpperLeft().getY());
        //START HERE
        if (updatedUpperLeft.getX()+width >= g.getScreenWidth()
                && updatedUpperLeft.getX()<=g.getScreenWidth()
                &&shadowPaddle!=null) {
            //overWidth represents excess 'paddle' over the border
            double overWidth = updatedUpperLeft.getX()+width-g.getScreenWidth();
            Point leftEdge = new Point(-(width-overWidth),collisionObject.getUpperLeft().getY());
            updatedCollisionObject = new Rectangle(leftEdge, width, height);
            updatedCollisionObject.setColor(collisionObject.getColor());
            shadowPaddle.collisionObject = updatedCollisionObject;
            if(shadowPaddle.isInGame==false){
                shadowPaddle.addToGame();
            }
            updatedCollisionObject = new Rectangle(updatedUpperLeft, width, height);
            updatedCollisionObject.setColor(collisionObject.getColor());
            removeFromGame();
            collisionObject = updatedCollisionObject;
            addToGame();
        } else if (updatedUpperLeft.getX()>g.getScreenWidth()
        &&shadowPaddle!=null) {
            removeFromGame();
            collisionObject = shadowPaddle.getCollisionRectangle();
            shadowPaddle.removeFromGame();
            addToGame();
        } else {
            updatedCollisionObject = new Rectangle(updatedUpperLeft, width, height);
            updatedCollisionObject.setColor(collisionObject.getColor());
            removeFromGame();
            collisionObject = updatedCollisionObject;
            addToGame();
        }
    }

    /**
     * Moves the paddle left. If the paddle is at the left edge of the screen, it teleports
     * to the right edge.
     */
    public void moveLeft() {
        Rectangle updatedCollisionObject;
        Point updatedUpperLeft = new Point(collisionObject.getUpperLeft().getX() - moveSpeed,
                collisionObject.getUpperLeft().getY());
        //START HERE
        if (updatedUpperLeft.getX() <= 0
                && updatedUpperLeft.getX()+width>=0
                &&shadowPaddle!=null) {
            //overWidth represents excess 'paddle' over the border
            double overWidth = -updatedUpperLeft.getX();
            Point leftEdge = new Point(g.getScreenWidth()-overWidth,collisionObject.getUpperLeft().getY());
            updatedCollisionObject = new Rectangle(leftEdge, width, height);
            updatedCollisionObject.setColor(collisionObject.getColor());
            shadowPaddle.collisionObject = updatedCollisionObject;
            if(shadowPaddle.isInGame==false){
                shadowPaddle.addToGame();
            }
            updatedCollisionObject = new Rectangle(updatedUpperLeft, width, height);
            updatedCollisionObject.setColor(collisionObject.getColor());
            removeFromGame();
            collisionObject = updatedCollisionObject;
            addToGame();
        } else if (updatedUpperLeft.getX()+width<0
                &&shadowPaddle!=null) {
            removeFromGame();
            collisionObject = shadowPaddle.getCollisionRectangle();
            shadowPaddle.removeFromGame();
            addToGame();
        } else {
            updatedCollisionObject = new Rectangle(updatedUpperLeft, width, height);
            updatedCollisionObject.setColor(collisionObject.getColor());
            removeFromGame();
            collisionObject = updatedCollisionObject;
            addToGame();
        }
    }

    /**
     * Method representing the time passed for the paddle. Checks for player input.
     */
    public void timePassed() {
        if (keyboard.isPressed("a")) {
            moveLeft();
            canMove = false;
        } else if (keyboard.isPressed("d")) {
            moveRight();
            canMove = false;
        } else if (keyboard.isPressed("A")) {
            moveLeft();
            canMove = false;
        } else if (keyboard.isPressed("D")) {
            moveRight();
            canMove = false;
        } else if (keyboard.isPressed("ש")) {
            moveLeft();
            canMove = false;
        } else if (keyboard.isPressed("ג")) {
            moveRight();
            canMove = false;
        } else if (keyboard.isPressed(keyboard.LEFT_KEY)) {
            moveLeft();
            canMove = false;
        } else if (keyboard.isPressed(keyboard.RIGHT_KEY)) {
            moveRight();
            canMove = false;
        } else {
            canMove = true;
        }
    }

    /**
     * Method to draw the paddle on a given drawsurface.
     *
     * @param d drawsurface to draw on.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(collisionObject.getColor());
        d.fillRectangle((int) collisionObject.getUpperLeft().getX(),
                (int) collisionObject.getUpperLeft().getY(),
                (int) collisionObject.getWidth(),
                (int) collisionObject.getHeight());
    }

    /**
     * Returns the collision rectangle of the paddle.
     *
     * @return the collision rectangle of the paddle.
     */
    public Rectangle getCollisionRectangle() {
        return collisionObject;
    }

    /**
     * Calculates and returns the new velocity of the ball after it hits the paddle.
     * game.geometry.Paddle has five regions, each with a different angle of velocity change.
     * If the ball hits the paddle below the top surface, paddle acts as a normal block.
     *
     * @param collisionPoint  the point where the collision occurred
     * @param currentVelocity the current velocity of the object
     * @return updated velocity after the hit.
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        //If the collision is below the paddle top, act as a normal block
        if (collisionPoint.getY() > collisionObject.getUpperLeft().getY()) {
            Rectangle blockPaddle = new Rectangle(collisionObject.getUpperLeft(),
                    width,
                    height);
            return blockPaddle.hit(collisionPoint, currentVelocity);
        }
        double region1 = collisionObject.getUpperLeft().getX();
        double region2 = region1 + width / 5;
        double region3 = region2 + width / 5;
        double region4 = region3 + width / 5;
        double region5 = region4 + width / 5;
        double ballLoc = collisionPoint.getX();
        if (region1 <= ballLoc && ballLoc <= region2) {
            currentVelocity.changeAngle(300);
        } else if (region2 <= ballLoc && ballLoc <= region3) {
            currentVelocity.changeAngle(330);
        } else if (region3 <= ballLoc && ballLoc <= region4) {
            currentVelocity.setVelocity(currentVelocity.getDx(), -currentVelocity.getDy());
        } else if (region4 <= ballLoc && ballLoc <= region5) {
            currentVelocity.changeAngle(30);
        } else {
            currentVelocity.changeAngle(60);
        }
        return currentVelocity;
    }

    /**
     * Method to add the paddle to the game.
     */
    public void addToGame() {
        g.addCollidable(this);
        g.addSprite(this);
        isInGame = true;
    }

    /**
     * Method to add the paddle to the game.
     */
    public void removeFromGame() {
        g.removeCollidable(this);
        g.removeSprite(this);
        isInGame = false;
    }
}
