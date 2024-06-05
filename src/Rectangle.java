import java.util.ArrayList;

public class Rectangle {
    private final Point upperLeft;
    private final double width;
    private final double height;

    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> result = new ArrayList<>();
        for (Line recLine : this.rectangleToLines()) {
            if (recLine.intersectionWith(line) != null) {
                result.add(recLine.intersectionWith(line));
            }
        }
        return result;
    }

    public java.util.List<Line> rectangleToLines() {
        java.util.List<Line> lineList = new ArrayList<>();
        lineList.add(new Line(upperLeft.getX(),
                upperLeft.getY(),
                upperLeft.getX(),
                upperLeft.getY() + height));
        lineList.add(new Line(upperLeft.getX(),
                upperLeft.getY(),
                upperLeft.getX() + width,
                upperLeft.getY()));
        lineList.add(new Line(upperLeft.getX() + width,
                upperLeft.getY(),
                upperLeft.getX() + width,
                upperLeft.getY() + height));
        lineList.add(new Line(upperLeft.getX(),
                upperLeft.getY() + height,
                upperLeft.getX() + width,
                upperLeft.getY() + height));
        return lineList;
    }

    // Return the width and height of the rectangle
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    // Returns the upper-left point of the rectangle.
    public Point getUpperLeft() {
        return upperLeft;
    }
}
