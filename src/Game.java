import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import org.w3c.dom.css.Rect;

import java.awt.Color;
import java.util.ArrayList;

public class Game {
    private final SpriteCollection sprites = new SpriteCollection();
    private final GameEnvironment environment = new GameEnvironment();
    private GUI gui;
    private final Sleeper sleeper = new Sleeper();
    private int screenWidth;
    private int screenHeight;
    public Game(int width,int height) {
        screenWidth =  width;
        screenHeight = height;
    }
    public int getScreenWidth() {
        return screenWidth;
    }
    public int getScreenHeight() {
        return screenHeight;
    }

    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }
    public void removeCollidable(Collidable c){
        this.environment.removeCollidable(c);
    }
    public void removeSprite(Sprite s){
        this.sprites.removeSprite(s);
    }
    private java.util.ArrayList<Rectangle> createBlockList(){
        java.util.ArrayList<Rectangle> blockList = new java.util.ArrayList<>();
        int numBlocksPerRow = 10;
        int numRows = 5;
        int padding = 4;
        int blockWidthArea = screenWidth;
        int blockHeightArea = 150;
        int blockWidth = ( blockWidthArea - (numBlocksPerRow + 1) * padding) / numBlocksPerRow;
        int blockHeight = ( blockHeightArea - (numRows + 1) * padding) / numRows;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numBlocksPerRow; col++) {
                int x = col * (blockWidth + padding) + padding;
                int y = row * (blockHeight + padding) + padding;
                Rectangle block = new Rectangle(new Point(x, y), blockWidth, blockHeight);
                block.setColor(Color.BLUE);
                blockList.add(block);
            }
        }
        return blockList;
    }

    // Initialize a new game: create the Blocks and Ball (and Paddle)
    // and add them to the game.
    public void initialize() {
        gui = new GUI("My game", screenWidth, screenHeight);
        //Setting screen borders
        //Top
        environment.addCollidable(new Rectangle(new Point(0, -40), screenWidth, 40));
        //Left
        environment.addCollidable(new Rectangle(new Point(-40, 0), 40, screenHeight));
        //Right
        environment.addCollidable(new Rectangle(new Point(screenWidth, 0), 40, screenHeight));
        //Bottom
        environment.addCollidable(new Rectangle(new Point(0, screenHeight), screenWidth, 40));
        //Creating the ball
        Ball ball = new Ball(370, 350, 5, Color.BLACK, environment);
        ball.setVelocity(0, 3);
        ball.addToGame(this);
        //TODO Blocks, Paddle
        ArrayList<Rectangle> blocks = this.createBlockList();
        for(Rectangle block:blocks){
            block.addToGame(this);
        }
        //Paddle
        Paddle player = new Paddle(gui,this,3,200,20);
        player.addToGame();
    }

    // Run the game -- start the animation loop.
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}
