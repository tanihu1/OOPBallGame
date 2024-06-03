import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a border of a rectangle.
 * A border is defined by four lines: leftX, rightX, topY, and bottomY.
 */
public class Border {
    private final Point start;
    private final Point end;
    private final double cmp = 0.00001;

    /**
     * Creates a new Border object with the given points.
     *
     * @param a The first point defining the border.
     * @param b The second point defining the border.
     */
    public Border(Point a, Point b) {
        start = a;
        end = b;
    }
    /**
     * Simple double comparing function.
     * @param x first double.
     * @param y second double.
     * @return true if doubles are equal up to 5 digits.
     */
    private boolean dCmp(double x, double y) {
        return Math.abs(x - y) <= cmp;
    }

    /**
     * Start point accessor.
     *
     * @return start point
     */
    public Point getStart() {
        return start;
    }

    /**
     * End point accessor.
     *
     * @return end point.
     */
    public Point getEnd() {
        return end;
    }

    /**
     * A function to determine the closest axis of a point to a border.
     *
     * @param a The point for which the closest axis needs to be determined.
     * @return The index of the closest axis (0 for leftX, 1 for topY, 2 for rightX, 3 for bottomY).
     */
    public int closetAxis(Point a) {
        ArrayList<Double> arr = new ArrayList<>();
        arr.add(Math.abs(a.getX() - start.getX()));
        arr.add(Math.abs(a.getY() - start.getY()));
        arr.add(Math.abs(a.getX() - end.getX()));
        arr.add(Math.abs(a.getY() - end.getY()));
        double min = Collections.min(arr);
        if (dCmp(min, arr.get(0))) {
            return 0;
        }
        if (dCmp(min, arr.get(1))) {
            return 1;
        }
        if (dCmp(min, arr.get(2))) {
            return 2;
        }
        return 3;
    }
}
