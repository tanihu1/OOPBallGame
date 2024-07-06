package game.geometry;

import java.util.ArrayList;

/**
 * This class represents a line in 2D space.
 * The line is defined by its start and end points.
 * It also has methods for calculating its length and slope.
 */
public class Line {
    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;
    private final Point start;
    private final Point end;
    private final double cmp = 0.00001;

    /**
     * Constructs a line segment with the given start and end points.
     *
     * @param start The start point of the line segment.
     * @param end   The end point of the line segment.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.x1 = start.getX();
        this.y1 = start.getY();
        this.x2 = end.getX();
        this.y2 = end.getY();
    }

    /**
     * Constructs a line segment with the given coordinates of the start and end points.
     *
     * @param x1 The x-coordinate of the start point.
     * @param y1 The y-coordinate of the start point.
     * @param x2 The x-coordinate of the end point.
     * @param y2 The y-coordinate of the end point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Simple double comparing function.
     *
     * @param x first double.
     * @param y second double.
     * @return true if doubles are equal up to 5 digits.
     */
    private boolean dCmp(double x, double y) {
        return Math.abs(x - y) <= cmp;
    }

    /**
     * Returns the length of the line segment.
     *
     * @return The length of the line segment.
     */
    public double length() {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    /**
     * @return The middle point of the line segment.
     */
    public Point middle() {
        double midX = (x1 + x2) / 2;
        double midY = (y1 + y2) / 2;
        return new Point(midX, midY);
    }

    /**
     * @return The start point of the line segment.
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return The end point of the line segment.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns the slope of the line segment.
     * Returns NaN if the line segment is vertical.
     *
     * @return The slope of the line segment.
     */
    public double slope() {
        return (this.y2 - this.y1) / (this.x2 - this.x1);
    }

    /**
     * Returns true if the given point is on the line segment, false otherwise.
     *
     * @param p The point to check.
     * @param a One end of the line segment.
     * @param b The other end of the line segment.
     * @return True if the point is on the line segment, false otherwise.
     */
    private boolean onSegment(Point p, Point a, Point b) {
        //Testing the two segments created by the points
        if (a.getX() <= p.getX() && p.getX() <= b.getX()
                && ((a.getY() <= p.getY() && p.getY() <= b.getY())
                || (b.getY() <= p.getY() && p.getY() <= a.getY()))) {
            return true;
        }
        return b.getX() <= p.getX() && p.getX() <= a.getX()
                && (b.getY() <= p.getY() && p.getY() <= a.getY()
                || (a.getY() <= p.getY() && p.getY() <= b.getY()));
    }

    /**
     * Returns true if this line segment intersects with the given line segment, false otherwise.
     *
     * @param other The line segment to check for intersection.
     * @return True if the line segments intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        if (this.equals(other)) {
            return true;
        }
        //Calculation orientations of current line:
        double o1 = ((other.end.getY() - other.start.getY()) * (this.end.getX() - this.start.getX()))
                - ((other.end.getX() - other.start.getX()) * (this.end.getY() - this.start.getY()));

        // Calculate the orientation of the other line
        double o2 = ((this.end.getY() - this.start.getY()) * (other.end.getX() - other.start.getX()))
                - ((this.end.getX() - this.start.getX()) * (other.end.getY() - other.start.getY()));
        Point intersection = this.intersectionWith(other);
        if (intersection == null) {
            return false;
        }
        if (o1 != o2) {
            return onSegment(intersection, this.start, this.end)
                    && onSegment(intersection, other.start, other.end);
        } else {
            return true;
        }
    }

    /**
     * Returns true if this line segment intersects with both of the given line segments, false otherwise.
     *
     * @param other1 The first line segment to check for intersection.
     * @param other2 The second line segment to check for intersection.
     * @return True if the line segments intersect, false otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return this.isIntersecting(other1) && this.isIntersecting(other2);
    }

    /**
     * Returns the point of intersection between this line segment and the given line segment.
     * Returns null if the line segments do not intersect.
     *
     * @param other The line segment to find the intersection with.
     * @return The point of intersection between the line segments, or null if they do not intersect.
     */
    public Point intersectionWith(Line other) {
        // toTest is the intersection of the infinite lines
        Point toTest = infiniteIntersection(other);
        // Testing if the intersection exists
        if (toTest == null) {
            return null;
        }
        // Making sure the intersection is on the line segments
        if (onSegment(toTest, this.start, this.end)
                && onSegment(toTest, other.start, other.end)) {
            return toTest;
        }
        return null;
    }

    /**
     * Returns the point of intersection between this line segment and the given line segment,
     * treating the lines as infinite.
     * Returns null if the lines are parallel and do not overlap.
     *
     * @param other The line segment to find the intersection with.
     * @return The point of intersection between the lines, or null if the lines are parallel and do not overlap.
     */
    private Point infiniteIntersection(Line other) {
        //Handling same lines
        if (this.equals(other)) {
            return null;
        }
        //Handling all parallel cases
        if (this.equalsNoLimit(other)) {
            if (this.slope() != 0) {
                double y2 = Math.max(this.y1, this.y2);
                double y1 = Math.min(this.y1, this.y2);
                double y4 = Math.max(other.y2, other.y1);
                double y3 = Math.min(other.y2, other.y1);
                //Lines overlap - more than one point of intersection
                if (y2 > y3 && y2 < y4) {
                    return null;
                }
                if (y1 < y4 && y1 > y3) {
                    return null;
                }
                //Lines intersect at exactly one point
                if (dCmp(y1, y4)) {
                    return new Point(x1, y1);
                }
                if (dCmp(y2, y3)) {
                    return new Point(x1, y2);
                }
                //Lines are parallel to Y Axis and do not intersect
                return null;
            } else {
                double x2 = Math.max(this.x1, this.x2);
                double x1 = Math.min(this.x1, this.x2);
                double x4 = Math.max(other.x2, other.x1);
                double x3 = Math.min(other.x2, other.x1);
                //Lines overlap - more than one point of intersection
                if (x2 > x3 && x2 < x4) {
                    return null;
                }
                if (x1 < x4 && x1 > x3) {
                    return null;
                }
                //Lines intersect at exactly one point
                if (dCmp(x1, x4)) {
                    return new Point(x1, this.y1);
                }
                if (dCmp(x2, x3)) {
                    return new Point(x2, this.y1);
                }
                //Lines are parallel to X Axis and do not intersect
                return null;

            }
        }
        if (Double.isInfinite(this.slope())) {
            return new Point(this.x1, other.slope() * this.x1 + other.yIntersect().getY());
        }
        if (Double.isInfinite(other.slope())) {
            return new Point(other.x1, this.slope() * other.x1 + this.yIntersect().getY());
        }
        double firstY = this.yIntersect().getY();
        double secondY = other.yIntersect().getY();
        double x = (firstY - secondY) / (other.slope() - this.slope());
        double y = this.slope() * x + firstY;
        return new Point(x, y);
    }

    /**
     * Returns the point of intersection of the line with the Y Axis.
     * Returns null if the line is vertical.
     *
     * @return The point of intersection of the line with the Y Axis.
     */
    public Point yIntersect() {
        double m = this.slope();
        if (Double.isNaN(m)) {
            return null;
        }
        return new Point(0, y1 - m * x1);
    }

    /**
     * Tests if the two lines are the same by their end and start points.
     *
     * @param other The line to compare to.
     * @return true if the lines are the same or false otherwise.
     */
    public boolean equals(Line other) {
        if (this.start().equals(other.start()) && this.end().equals(other.end())) {
            return true;
        }
        return this.start().equals(other.end()) && this.end().equals(other.start());
    }

    /**
     * Tests if the lines have the same equation y=mx+b.
     *
     * @param other line to compare to.
     * @return true if the lines share the same equation or false otherwise.
     */
    public boolean equalsNoLimit(Line other) {
        //Testing to see if both lines are parallel
        if (Double.isInfinite(this.slope())) {
            if (Double.isInfinite(other.slope())) {
                return dCmp(this.x1, other.x1);
            } else {
                return false;
            }
        }
        //If one line is parallel and one is not, they do not equal
        if (Double.isInfinite(this.slope()) || Double.isInfinite(other.slope())) {
            return false;
        }
        return this.yIntersect().equals(other.yIntersect()) && dCmp(this.slope(), other.slope());
    }

    /**
     * Determines if a point lies on the line segment defined by start and end points.
     *
     * @param a The point to check.
     * @return True if the point is on the line segment, false otherwise.
     */
    public Boolean pointOnLine(Point a) {
        return (a.getY() >= start.getY() && a.getY() <= end.getY()
                && a.getX() >= start.getX() && a.getX() <= end.getX())
                || (a.getY() <= start.getY() && a.getY() >= end.getY()
                && a.getX() <= start.getX() && a.getX() >= end.getX());
    }

    /**
     * Calculates the intersection points between this game.geometry.Line and a circle defined by the game.geometry.Ball object.
     *
     * @param circ The game.geometry.Ball object representing the circle to intersect with.
     * @return An ArrayList of game.geometry.Point objects representing the intersection points.
     */
    public ArrayList<Point> intersectionWithCirc(Ball circ) {
        Point pointA = this.start;
        Point pointB = this.end;
        Point center = new Point(circ.getX(), circ.getY());
        double radius = circ.getSize();
        double baX = pointB.getX() - pointA.getX();
        double baY = pointB.getY() - pointA.getY();
        double caX = center.getX() - pointA.getX();
        double caY = center.getY() - pointA.getY();

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return new ArrayList<>();
        }
        // if disc == 0 ... dealt with later
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;

        Point p1 = new Point(pointA.getX() - baX * abScalingFactor1, pointA.getY()
                - baY * abScalingFactor1);
        if (dCmp(disc, 0)) { // abScalingFactor1 == abScalingFactor2
            ArrayList<Point> intersections = new ArrayList<>();
            intersections.add(p1);
            return intersections;
        }
        Point p2 = new Point(pointA.getX() - baX * abScalingFactor2, pointA.getY()
                - baY * abScalingFactor2);
        ArrayList<Point> intersections = new ArrayList<>();
        intersections.add(p1);
        intersections.add(p2);
        return intersections;
    }

    /**
     * Returns the closest intersection point with the rectangle to the start of the line.
     *
     * @param rect rectangle to test intersection with.
     * @return the closest intersection point with the rectangle to the start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> intersections = rect.intersectionPoints(this);
        Point result = null;
        double currentDistance = Double.POSITIVE_INFINITY;
        for (Point point : intersections) {
            if (this.start().distance(point) < currentDistance) {
                result = point;
            }
        }
        return result;
    }
}