package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import game.hitListeners.BallRemover;
import game.hitListeners.BlockRemover;
import game.hitListeners.ScoreTrackingListener;
import game.interfaces.Collidable;
import game.geometry.*;
import game.interfaces.HitListener;
import game.interfaces.Sprite;
import game.sprites.ScoreIndicator;
import game.sprites.SpriteCollection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * game.Game class containing the logic of the game.
 */
public class Game {
    private final SpriteCollection sprites = new SpriteCollection();
    private final GameEnvironment environment = new GameEnvironment();
    private GUI gui;
    private final Sleeper sleeper = new Sleeper();
    private int screenWidth;
    private int screenHeight;
    private List<HitListener> hitListeners = new ArrayList<>();
    //Counters
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;
    //Score indicator
    private ScoreIndicator scoreDisplay;

    /**
     * game.Game constructor.
     *
     * @param width  GUI width.
     * @param height GUI height.
     */
    public Game(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    /**
     * Screen width getter.
     *
     * @return screen width value;
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Screen height getter.
     *
     * @return screen height value.
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Method to add a collidable to the game environment.
     *
     * @param c collidable to add.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Method to add a sprite to the game.
     *
     * @param s sprite to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Method to remove a collidable from the game environment.
     *
     * @param c collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        this.environment.addToRemoveQueue(c);
    }

    /**
     * Method to remove a sprite from the game.
     *
     * @param s sprite to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Method to create the blocks in the game.
     *
     * @return a block list with all the blocks in the game.
     */
    private java.util.ArrayList<Block> createBlockList() {
        java.util.ArrayList<Block> blockList = new java.util.ArrayList<>();
        int topPadding = 50;
        //First row
        for (int i = 0; i < 12; i++) {
            Block toAdd = new Block(new Point(screenWidth - i * 40 - 65 - i * 2, topPadding), 40, 20, this);
            toAdd.setColor(Color.RED);
            blockList.add(toAdd);
        }
        //Second row
        for (int i = 0; i < 11; i++) {
            Block toAdd = new Block(new Point(screenWidth - i * 40 - 65 - i * 2, topPadding + 22), 40, 20, this);
            toAdd.setColor(Color.GRAY);
            blockList.add(toAdd);
        }
        //Third row
        for (int i = 0; i < 10; i++) {
            Block toAdd = new Block(new Point(screenWidth - i * 40 - 65 - i * 2, topPadding + 44), 40, 20, this);
            toAdd.setColor(Color.CYAN);
            blockList.add(toAdd);
        }
        //Forth row
        for (int i = 0; i < 9; i++) {
            Block toAdd = new Block(new Point(screenWidth - i * 40 - 65 - i * 2, topPadding + 66), 40, 20, this);
            toAdd.setColor(Color.PINK);
            blockList.add(toAdd);
        }
        //Fifth row
        for (int i = 0; i < 8; i++) {
            Block toAdd = new Block(new Point(screenWidth - i * 40 - 65 - i * 2, topPadding + 88), 40, 20, this);
            toAdd.setColor(Color.MAGENTA);
            blockList.add(toAdd);
        }
        //Sixth row
        for (int i = 0; i < 7; i++) {
            Block toAdd = new Block(new Point(screenWidth - i * 40 - 65 - i * 2, topPadding + 110), 40, 20, this);
            toAdd.setColor(Color.YELLOW);
            blockList.add(toAdd);
        }
        return blockList;
    }

    /**
     * Initializes the game values.
     */
    public void initialize() {
        gui = new GUI("My game", screenWidth, screenHeight);
        //Setting screen color
        Rectangle background = new Rectangle(new Point(0, 0), new Point(screenWidth, screenHeight));
        background.setColor(Color.BLUE);
        sprites.addSprite(background);
        //Setting screen borders
        Rectangle top = new Rectangle(new Point(0, 0), new Point(800, 20));
        Rectangle left = new Rectangle(new Point(0, 0), new Point(20, 600));
        Rectangle bottom = new Rectangle(new Point(0, 599), new Point(800, 600));
        Rectangle right = new Rectangle(new Point(780, 0), new Point(800, 600));
        top.setColor(Color.GRAY);
        left.setColor(Color.GRAY);
        bottom.setColor(Color.GRAY);
        right.setColor(Color.GRAY);
        environment.addCollidable(top);
        environment.addCollidable(bottom);
        environment.addCollidable(right);
        environment.addCollidable(left);
        sprites.addSprite(top);
        sprites.addSprite(right);
        sprites.addSprite(left);
        //Creating the balls
        Ball ball1 = new Ball(300, 300, 5, Color.BLACK, environment, screenWidth, screenHeight);
        Ball ball2 = new Ball(370, 350, 5, Color.BLACK, environment, screenWidth, screenHeight);
        Ball ball3 = new Ball(370, 350, 5, Color.BLACK, environment, screenWidth, screenHeight);
        ball1.setVelocity(-3, -3);
        ball1.addToGame(this);
        ball2.setVelocity(3, -3);
        ball2.addToGame(this);
        ball3.setVelocity(3, 3);
        ball3.addToGame(this);
        ArrayList<Block> blocks = this.createBlockList();
        for (Block block : blocks) {
            block.addToGame(this);
        }
        //game.geometry.Paddle
        Paddle player = new Paddle(gui, this, 3, 200, 20);
        player.addToGame();
        //Initializing Counters
        remainingBlocks = new Counter();
        remainingBlocks.increase(blocks.size());
        remainingBalls = new Counter();
        remainingBalls.increase(3);
        //Score
        score = new Counter();
        scoreDisplay = new ScoreIndicator(score);
        sprites.addSprite(scoreDisplay);
        //Initializing hit listeners
        hitListeners.add(new BlockRemover(this,remainingBlocks));
        hitListeners.add(new ScoreTrackingListener((score)));
        BallRemover deathZone = new BallRemover(this,remainingBalls);
        //Registering hit listeners
        for(HitListener listener:hitListeners) {
            for(Block b:blocks) {
                b.addHitListener(listener);
            }
        }
        bottom.addHitListener(deathZone);
    }

    /**
     * Starts the animation loop and runs the game.
     */
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            //Game condition
            if (remainingBlocks.isEmpty()) {
                score.increase(100);
                return;
            }
            if(remainingBalls.isEmpty()) {
                return;
            }
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            environment.removeCollidables();
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
