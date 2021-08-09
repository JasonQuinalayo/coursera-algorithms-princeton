import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;
import java.util.LinkedList;

public class BruteCollinearPoints {
    private int numOfSegments = 0;
    private final LineSegment[] lineSegmentsArray;

    public BruteCollinearPoints(Point[] points) {
        validatePointsArray(points);
        LinkedList<LineSegment> lineSegments = new LinkedList<LineSegment>();
        for (int a = 0; a < points.length; a++) {
            for (int b = a + 1; b < points.length; b++) {
                validatePoints(points[a], points[b]);
                for (int c = b + 1; c < points.length; c++) {
                    for (int d = c + 1; d < points.length; d++) {
                        if (areCollinear(points[a], points[b], points[c], points[d])) {
                            lineSegments.add(collinearPointsToLineSegment(
                                    new Point[] { points[a], points[b], points[c], points[d] }));
                            numOfSegments++;
                        }
                    }
                }
            }
        }
        lineSegmentsArray = lineSegments.toArray(new LineSegment[0]);
    }

    private LineSegment collinearPointsToLineSegment(Point[] points) {
        Point minimum = points[0];
        Point maximum = points[0];
        for (int i = 0; i < points.length; i++) {
            if (points[i].compareTo(minimum) < 0) minimum = points[i];
            if (points[i].compareTo(maximum) > 0) maximum = points[i];
        }
        return new LineSegment(minimum, maximum);
    }

    private void validatePointsArray(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }
    }

    private boolean areCollinear(Point a, Point b, Point c, Point d) {
        return a.slopeTo(b) == a.slopeTo(c) && a.slopeTo(b) == a.slopeTo(d);
    }

    private void validatePoints(Point a, Point b) {
        if (a == null || b == null || a.compareTo(b) == 0) {
            throw new IllegalArgumentException();
        }
    }

    public int numberOfSegments() {
        return numOfSegments;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(lineSegmentsArray, lineSegmentsArray.length);
    }

    public static void main(String[] args) {
        Point[] points;
        int size = StdIn.readInt();
        points = new Point[size];
        int i = 0;
        while (!(StdIn.isEmpty())) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            Point point = new Point(x, y);
            points[i] = point;
            i++;
        }
        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        System.out.println(Arrays.toString(bruteCollinearPoints.segments()));
    }
}
