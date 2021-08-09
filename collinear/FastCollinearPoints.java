import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private final LinkedList<LineSegment> lineSegments = new LinkedList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        checkForNull(points);
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        for (int i = 0; i < pointsCopy.length; i++) {
            Point[] pointsArrayPerPoint = Arrays.copyOf(pointsCopy, pointsCopy.length);
            Arrays.sort(pointsArrayPerPoint, pointsCopy[i].slopeOrder());
            addLineSegments(pointsArrayPerPoint);
        }
    }

    private void checkForNull(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }
    }

    private void addLineSegments(Point[] points) {
        if (points.length < 2) {
            return;
        }
        int startIndex = 1;
        int endIndex;
        double previousSlope = points[0].slopeTo(points[1]);
        double currentSlope;
        for (int i = 1; i < points.length; i++) {
            checkForRepeatedPoints(points[0], points[i]);
            currentSlope = points[0].slopeTo(points[i]);
            if (i != 1 && currentSlope != previousSlope) {
                endIndex = i - 1;
                if (endIndex - startIndex >= 2)
                    addOneLineSegment(points[0], points[startIndex], points[endIndex]);
                startIndex = i;
            }
            if (i == points.length - 1) {
                endIndex = i;
                if (endIndex - startIndex >= 2)
                    addOneLineSegment(points[0], points[startIndex], points[endIndex]);
            }
            previousSlope = currentSlope;
        }
    }

    private void checkForRepeatedPoints(Point a, Point b) {
        if (a.compareTo(b) == 0) {
            throw new IllegalArgumentException();
        }
    }

    private void addOneLineSegment(Point source, Point start, Point end) {
        if (source.compareTo(start) < 0) {
            lineSegments.add(new LineSegment(source, end));
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
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
        FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points);
        System.out.println(Arrays.toString(fastCollinearPoints.segments()));
    }
}
