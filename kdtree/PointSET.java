import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.LinkedList;

public class PointSET {
    private final SET<Point2D> set;

    public PointSET() {
        set = new SET<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D point) {
        set.add(point);
    }

    public boolean contains(Point2D point) {
        return set.contains(point);
    }

    public void draw() {
        StdDraw.setPenRadius(0.01);
        for (Point2D point : set)
            point.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        LinkedList<Point2D> range = new LinkedList<Point2D>();
        for (Point2D point : set) {
            if (rect.contains(point)) {
                range.add(point);
            }
        }
        return range;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (set.isEmpty()) {
            return null;
        }
        Point2D nearest = null;
        for (Point2D point : set) {
            if (nearest == null || point.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
                nearest = point;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        PointSET pointSET = new PointSET();
        while (!(StdIn.isEmpty())) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            pointSET.insert(new Point2D(x, y));
        }
        Stopwatch stopwatch = new Stopwatch();
        System.out.println(pointSET.nearest(new Point2D(0, 0.6)));
        System.out.println(stopwatch.elapsedTime());
    }
}
