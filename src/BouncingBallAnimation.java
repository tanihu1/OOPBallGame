//318792801
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * Class for the single bouncing ball exercise.
 */
public class BouncingBallAnimation {
    private static final String ISNUMBER = "^[0-9]+$";

    /**
     * Draws an animation of a bouncing ball on the GUI.
     *
     * @param start The starting point of the ball
     * @param dx    The change in x-coordinate
     * @param dy    The change in y-coordinate
     */
    static void drawAnimation(Point start, double dx, double dy) {
        GUI gui = new GUI("BouncingBallAnimation", 200, 200);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(start.getX(), start.getY(), 5, java.awt.Color.BLACK);
        //Setting velocity and borders for the ball
        Point border1 = new Point(0, 0);
        Point border2 = new Point(200, 200);
        ball.addBorder(border1, border2);
        ball.setVelocity(dx, dy);
        //Animation loop
        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50); // wait for 50 milliseconds.
        }
    }

    /**
     * main function that processes arguments and calls the draw func.
     *
     * @param args arguments received from CLI.
     */
    public static void main(String[] args) {
        //Testing arguments
        if (args.length != 4) {
            System.out.println("Bad arguments!");
            return;
        }
        for (String arg : args) {
            if (!arg.matches(ISNUMBER)) {
                System.out.println("Only number arguments!");
                return;
            }
        }
        int xVal = Integer.parseInt(args[0]);
        int yVal = Integer.parseInt(args[1]);
        int xVelocity = Integer.parseInt(args[2]);
        int yVelocity = Integer.parseInt(args[3]);
        //Validating start position.
        if (xVal > 200 || xVal < 0 || yVal > 200 || yVal < 0) {
            System.out.println("Bad arguments!");
            return;
        }
        drawAnimation(new Point(xVal, yVal), xVelocity, yVelocity);
    }
}