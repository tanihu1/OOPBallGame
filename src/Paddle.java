import biuoop.DrawSurface;
import biuoop.GUI;
import org.w3c.dom.ranges.Range;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Paddle implements Collidable, Sprite {
    private final biuoop.KeyboardSensor keyboard;
    private Rectangle collisionObject;
    private final double cmp = 0.00001;
    private int moveSpeed;
    private int width;
    private int height;
    private Game g;
    private boolean canMove = true;

    public Paddle(GUI gui, Game g, int moveSpeed, int width, int height) {
        this.keyboard = gui.getKeyboardSensor();
        this.moveSpeed = moveSpeed;
        this.width = width;
        this.height = height;
        this.g = g;
        collisionObject = new Rectangle(new Point(300, 580), width, height);
        collisionObject.setColor(Color.BLACK);
    }

    private boolean dCmp(double x, double y) {
        return Math.abs(x - y) <= cmp;
    }

    public void moveRight() {
        Point updatedUpperLeft = new Point(collisionObject.getUpperLeft().getX() + moveSpeed,
                collisionObject.getUpperLeft().getY());
        if(updatedUpperLeft.getX()+width>=g.getScreenWidth()) return;
        Rectangle updatedCollisionObject = new Rectangle(updatedUpperLeft, width, height);
        updatedCollisionObject.setColor(collisionObject.getColor());
        g.removeCollidable(this);
        g.removeSprite(this);
        collisionObject = updatedCollisionObject;
        g.addCollidable(this);
        g.addSprite(this);
    }
    public void moveLeft() {
        Point updatedUpperLeft = new Point(collisionObject.getUpperLeft().getX() - moveSpeed,
                collisionObject.getUpperLeft().getY());
        if(updatedUpperLeft.getX()<=0) return;
        Rectangle updatedCollisionObject = new Rectangle(updatedUpperLeft, width, height);
        updatedCollisionObject.setColor(collisionObject.getColor());
        g.removeCollidable(this);
        g.removeSprite(this);
        collisionObject = updatedCollisionObject;
        g.addCollidable(this);
        g.addSprite(this);
    }

    public void timePassed() {
        if (keyboard.isPressed("a")) {
            moveLeft();
            canMove = false;
        }else if (keyboard.isPressed("d")) {
            moveRight();
            canMove = false;
        }else if (keyboard.isPressed("A")) {
            moveLeft();
            canMove = false;
        }else if (keyboard.isPressed("D")) {
            moveRight();
            canMove = false;
        }else if (keyboard.isPressed("ש")) {
            moveLeft();
            canMove = false;
        }else if (keyboard.isPressed("ג")) {
            moveRight();
            canMove = false;
        }else if (keyboard.isPressed(keyboard.LEFT_KEY)) {
            moveLeft();
            canMove = false;
        }else if (keyboard.isPressed(keyboard.RIGHT_KEY)) {
            moveRight();
            canMove = false;
        }else{
            canMove = true;
        }
    }

    public void drawOn(DrawSurface d) {
        d.setColor(collisionObject.getColor());
        d.fillRectangle((int) collisionObject.getUpperLeft().getX(),
                (int) collisionObject.getUpperLeft().getY(),
                (int) collisionObject.getWidth(),
                (int) collisionObject.getHeight());
    }

    // Collidable
    public Rectangle getCollisionRectangle() {
        return collisionObject;
    }

    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
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

    // Add this paddle to the game.
    public void addToGame() {
        g.addCollidable(this);
        g.addSprite(this);
    }
}
