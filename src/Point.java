/**
 * Point class consisting of x and y coordinates.
 */
public class Point {
    private double x;
    private double y;
    private final double cmp = 0.00001;

    /**
     * Point constructor with two doubles.
     *
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
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
     * Calculates the distance between this point and another point.
     *
     * @param other the other Point object to calculate the distance to
     * @return the Euclidean distance between this point and the other point
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     * Set the x coordinate of the Point object.
     *
     * @param x the new x coordinate value
     */
    public void changeX(double x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the point object.
     *
     * @param y the new y-coordinate value
     */
    public void changeY(double y) {
        this.y = y;
    }

    /**
     * Compares this Point with another Point for equality.
     *
     * @param other the other Point object to compare with
     * @return true if the Points are equal, false otherwise
     */
    public boolean equals(Point other) {
        if (other == null) {
            return false;
        }
        return dCmp(this.x, other.x) && dCmp(this.y, other.y);
    }

    /**
     * Return the x value of this point.
     *
     * @return the x value of this point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Return the y value of this point.
     *
     * @return the y value of this point.
     */
    public double getY() {
        return this.y;
    }

    /**
     * An override of the toString method that returns the x and y coordinates in a formatted string.
     *
     * @return the formatted string representing the x and y coordinates
     */
    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
