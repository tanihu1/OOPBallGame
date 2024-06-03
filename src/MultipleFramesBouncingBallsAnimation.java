//318792801
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;

/**
 * Class for the Frames animation exercise.
 */
public class MultipleFramesBouncingBallsAnimation {
    private static final String ISNUMBER = "^[0-9]+$";

    /**
     * Sorts an array of doubles in descending order using bubble sort algorithm.
     *
     * @param arr the array of doubles to be sorted
     */
    private static void sortArray(double[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] < arr[j + 1]) {
                    double temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Draws multiple bouncing balls on the screen based on the given sizes array.
     *
     * @param sizes an array of sizes for the bouncing balls
     */
    public static void drawMultipleBalls(double[] sizes) {
        //Conditions
        final int borderX = 800;
        final int borderY = 600;
        final Point grayRectangleX = new Point(50, 50);
        final Point grayRectangleY = new Point(500, 500);
        final Point yellowRectangleX = new Point(450, 450);
        final Point yellowRectangleY = new Point(600, 600);
        final int maxVelocity = 20;
        final int minVelocity = 3;
        //Creating the velocities for the balls
        double[] velocities = new double[sizes.length];
        double spacing = (double) (maxVelocity - minVelocity) / (sizes.length - 1);
        velocities[0] = minVelocity;
        for (int i = 0; i < sizes.length; i++) {
            velocities[i] = (int) Math.round(minVelocity + spacing * i);
        }
        java.util.Random rand = new java.util.Random();
        //Creating balls in random coordinates
        Ball[] balls = new Ball[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            double xPos = rand.nextInt(borderX);
            double yPos = rand.nextInt(borderY);
            balls[i] = new Ball(xPos, yPos, (int) sizes[i], Color.BLACK);
            //Setting random color
            balls[i].setRandomColor();
        }
        //Setting velocities and borders
        for (int i = 0; i < balls.length; i++) {
            balls[i].setVelocity(velocities[i], velocities[i]);
            //First half gets gray border, other half gets the inverse borders
            if (i < balls.length / 2) {
                balls[i].addBorder(grayRectangleX, grayRectangleY);
            } else {
                Point screenX = new Point(0, 0);
                Point screenY = new Point(borderX, borderY);
                balls[i].addBorder(screenX, screenY);
                balls[i].addInverseBorder(grayRectangleX, grayRectangleY);
                balls[i].addInverseBorder(yellowRectangleX, yellowRectangleY);
            }
        }
        GUI gui = new GUI("Multiple frames balls", borderX, borderY);
        Sleeper sleeper = new Sleeper();
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            drawRecGray(d);
            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }
            drawRecYellow(d);
            gui.show(d);
            sleeper.sleepFor(50); // wait for 50 milliseconds.
        }

    }

    /**
     * Draws a gray rectangle on the given DrawSurface.
     *
     * @param d the DrawSurface to draw on
     */
    private static void drawRecGray(DrawSurface d) {
        d.setColor(Color.gray);
        d.fillRectangle(50,
                50,
                450,
                450);
    }

    /**
     * Draws a yellow rectangle on the given DrawSurface.
     *
     * @param d the DrawSurface to draw on
     */
    private static void drawRecYellow(DrawSurface d) {
        d.setColor(Color.yellow);
        d.fillRectangle(450,
                450,
                150,
                150);
    }

    /**
     * Main function that processes the input arguments, validates them,
     * sorts the sizes array, and draws multiple balls accordingly.
     *
     * @param args the array of input arguments
     */
    public static void main(String[] args) {
        double[] sizes = new double[args.length];
        for (int i = 0; i < args.length; i++) {
            if (!args[i].matches(ISNUMBER)) {
                System.out.println("Bad arguments!");
                return;
            }
            if (Double.parseDouble(args[i]) <= 0) {
                System.out.println("Bad arguments!");
                return;
            }
            if (Double.parseDouble(args[i]) >= 150) {
                System.out.println("Radius too big!");
                return;
            }
            sizes[i] = Double.parseDouble(args[i]);
        }
        sortArray(sizes);
        drawMultipleBalls(sizes);
    }
}
