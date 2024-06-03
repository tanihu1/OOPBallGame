//318792801
import biuoop.DrawSurface;
import biuoop.GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class for random line generation exercise.
 */
public class AbstractArtDrawing {
    /**
     * screen width.
     */
    private final int width = 400;
    /**
     * screen height.
     */
    private final int height = 300;

    /**
     * width accessor.
     *
     * @return screen width
     */
    public int getWidth() {
        return width;
    }

    /**
     * height accessor.
     *
     * @return screen height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Generates a random line with random coordinates within the specified
     * width and height.
     *
     * @return a Line object representing the random line generated.
     */
    private Line randomLine() {
        Random rand = new Random();
        int x1 = rand.nextInt(width);
        int y1 = rand.nextInt(height);
        int x2 = rand.nextInt(width);
        int y2 = rand.nextInt(height);
        return new Line(x1, y1, x2, y2);
    }

    /**
     * Generates 10 unique lines.
     * Draws line on screen.
     * Finds the intersections between lines.
     */
    public void generateRandomLines() {
        GUI gui = new GUI("Random lines", width, height);
        //Generating 10 random none-duplicate lines
        ArrayList<Line> generated = new ArrayList<Line>();
        while (generated.size() < 10) {
            Line toAdd = randomLine();
            boolean exists = false;
            for (Line line : generated) {
                if (toAdd.equals(line)) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                continue;
            }
            generated.add(toAdd);
        }
        //Draw each generated line on the surface
        DrawSurface surface = gui.getDrawSurface();
        for (Line line : generated) {
            int x1 = (int) line.start().getX();
            int y1 = (int) line.start().getY();
            int x2 = (int) line.end().getX();
            int y2 = (int) line.end().getY();
            int middleX = (int) line.middle().getX();
            int middleY = (int) line.middle().getY();
            surface.setColor(Color.BLACK);
            surface.drawLine(x1, y1, x2, y2);
            surface.setColor(Color.BLUE);
            surface.fillCircle(middleX, middleY, 3);
        }
        //Testing all possible intersection points to find triangles
        for (Line line1 : generated) {
            for (Line line2 : generated) {
                if (line1.equals(line2)) {
                    continue;
                }

                for (Line line3 : generated) {
                    if (line1.equals(line3) || line2.equals(line3)) {
                        continue;
                    }
                    //If 3 different intersecting lines = triangle.
                    if (line1.isIntersecting(line2, line3)
                            && line2.isIntersecting(line3, line1)) {
                        Point intersection12 = line1.intersectionWith(line2);
                        Point intersection13 = line1.intersectionWith(line3);
                        Point intersection23 = line2.intersectionWith(line3);
                        int x1 = (int) intersection12.getX();
                        int y1 = (int) intersection12.getY();
                        int x2 = (int) intersection13.getX();
                        int y2 = (int) intersection13.getY();
                        int x3 = (int) intersection23.getX();
                        int y3 = (int) intersection23.getY();
                        //Covering the triangle with green lines
                        surface.setColor(Color.GREEN);
                        surface.drawLine(x1, y1, x2, y2);
                        surface.drawLine(x2, y2, x3, y3);
                        surface.drawLine(x3, y3, x1, y1);
                    }
                }
            }
        }
        //Finding intersections and drawing points
        ArrayList<Point> intersections = new ArrayList<Point>();
        for (Line line : generated) {
            for (Line otherLine : generated) {
                if (line.equals(otherLine)) {
                    continue;
                }
                if (line.isIntersecting(otherLine)) {
                    //If two different intersecting lines were found,
                    //the intersection point is drawn
                    Point intersection = line.intersectionWith(otherLine);
                    for (Point point : intersections) {
                        if (point.equals(intersection)) {
                            continue;
                        }
                    }
                    intersections.add(intersection);
                    surface.setColor(Color.RED);
                    surface.fillCircle((int) intersection.getX(), (int) intersection.getY(), 3);
                }
            }
        }
        gui.show(surface);
    }
    /**
     * Main function that creates an instance of AbstractArtDrawing and generates random lines.
     *
     * @param  args    the array of input arguments
     */
    public static void main(String[] args) {
        AbstractArtDrawing m = new AbstractArtDrawing();
        m.generateRandomLines();
    }
}
