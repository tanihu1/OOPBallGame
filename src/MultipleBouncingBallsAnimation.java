//318792801
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;

/**
 * Class for the multiple balls animation exercise.
 */
public class MultipleBouncingBallsAnimation {
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
     * Draws multiple bouncing balls on the screen based on the provided sizes array,
     * with randomized velocities and positions within the specified borders.
     *
     * @param sizes an array of sizes for the bouncing balls
     */
    public static void drawMultipleBalls(double[] sizes) {
        //Conditions
        final int borderX = 600;
        final int borderY = 600;
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
        //Creating balls with random coordinates
        Ball[] balls = new Ball[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            double xPos = rand.nextInt(borderX);
            double yPos = rand.nextInt(borderY);
            balls[i] = new Ball(xPos, yPos, (int) sizes[i], Color.BLACK);
        }
        //Setting velocities and borders
        Point screenX = new Point(0, 0);
        Point screenY = new Point(borderX, borderY);
        for (int i = 0; i < balls.length; i++) {
            if (balls.length == 1) {
                balls[i].setVelocity(10, 10);
                balls[i].addBorder(screenX, screenY);
                break;
            }
            balls[i].setVelocity(velocities[i], velocities[i]);
            balls[i].addBorder(screenX, screenY);
        }
        GUI gui = new GUI("MultipleBallsAnimation", borderX, borderY);
        Sleeper sleeper = new Sleeper();
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(50); // wait for 50 milliseconds.
        }

    }

    /**
     * Main function that processes the input arguments, sorts the sizes, and draws multiple balls.
     *
     * @param args the input arguments to the program
     */
    public static void main(String[] args) {
        double[] sizes = new double[args.length];
        for (int i = 0; i < args.length; i++) {
            if (!args[i].matches(ISNUMBER)) {
                System.out.println("Bad arguments! (Not a number)");
                return;
            }
            if (Double.parseDouble(args[i]) <= 0) {
                System.out.println("Bad arguments! (Negative)");
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
