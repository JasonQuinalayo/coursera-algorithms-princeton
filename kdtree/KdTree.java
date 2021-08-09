import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

import java.util.LinkedList;

public class KdTree {
    private Point2D point2D = null;
    private KdTree left = null;
    private KdTree right = null;
    private int size = 0;
    private final RectHV rectHV;

    public KdTree() {
        rectHV = new RectHV(0, 0, 1, 1);
    }

    private KdTree(RectHV rect) {
        rectHV = rect;
    }


    private KdTree insertKdTree(Point2D aPoint2D, RectHV rect) {
        KdTree kdTree = new KdTree(rect);
        kdTree.point2D = aPoint2D;
        kdTree.size = 1;
        return kdTree;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (this.size == 0) {
            this.point2D = p;
            size++;
        }
        else {
            validateInput(p);
            insertToVerticalNode(p);
        }
    }

    private boolean insertToVerticalNode(Point2D p) {
        if (this.point2D.equals(p)) return false;
        boolean success = false;
        if (p.x() < this.point2D.x()) {
            if (this.left == null) {
                double xmin = this.rectHV.xmin();
                double ymin = this.rectHV.ymin();
                double xmax = this.point2D.x();
                double ymax = this.rectHV.ymax();
                this.left = insertKdTree(p, new RectHV(xmin, ymin, xmax, ymax));
                success = true;
            }
            else success = this.left.insertToHorizontalNode(p);
        }
        else {
            if (this.right == null) {
                double xmin = this.point2D.x();
                double ymin = this.rectHV.ymin();
                double xmax = this.rectHV.xmax();
                double ymax = this.rectHV.ymax();
                this.right = insertKdTree(p, new RectHV(xmin, ymin, xmax, ymax));
                success = true;
            }
            else success = this.right.insertToHorizontalNode(p);
        }
        if (success) {
            size++;
            return true;
        }
        return false;
    }

    private boolean insertToHorizontalNode(Point2D p) {
        if (this.point2D.equals(p)) return false;
        boolean success = false;
        if (p.y() < this.point2D.y()) {
            if (this.left == null) {
                double xmin = this.rectHV.xmin();
                double ymin = this.rectHV.ymin();
                double xmax = this.rectHV.xmax();
                double ymax = this.point2D.y();
                this.left = insertKdTree(p, new RectHV(xmin, ymin, xmax, ymax));
                success = true;
            }
            else success = this.left.insertToVerticalNode(p);
        }
        else {
            if (this.right == null) {
                double xmin = this.rectHV.xmin();
                double ymin = this.point2D.y();
                double xmax = this.rectHV.xmax();
                double ymax = this.rectHV.ymax();
                this.right = insertKdTree(p, new RectHV(xmin, ymin, xmax, ymax));
                success = true;
            }
            else success = this.right.insertToVerticalNode(p);
        }
        if (success) {
            size++;
            return true;
        }
        return false;
    }

    public boolean contains(Point2D p) {
        validateInput(p);
        return size != 0 && containsRecursive(p);
    }

    private boolean containsRecursive(Point2D p) {
        if (this.point2D.equals(p)) {
            return true;
        }
        if (this.right != null && this.right.rectHV.contains(p)) {
            return this.right.containsRecursive(p);
        }
        else if (this.left != null && this.left.rectHV.contains(p)) {
            return this.left.containsRecursive(p);
        }
        else return false;
    }

    public void draw() {
        StdDraw.setPenRadius(0.01);
        if (this.size != 0) {
            drawRecursive();
        }
    }

    private void drawRecursive() {
        this.point2D.draw();
        if (this.left != null) this.left.drawRecursive();
        if (this.right != null) this.right.drawRecursive();
    }

    public Iterable<Point2D> range(RectHV rect) {
        validateInput(rect);
        LinkedList<Point2D> linkedList = new LinkedList<Point2D>();
        rangeRecursive(rect, linkedList);
        return linkedList;
    }

    private void rangeRecursive(RectHV rect, LinkedList<Point2D> linkedList) {
        if (this.point2D != null && rect.contains(this.point2D)) {
            linkedList.add(this.point2D);
        }
        if (this.left != null && rect.intersects(this.left.rectHV))
            this.left.rangeRecursive(rect, linkedList);
        if (this.right != null && rect.intersects(this.right.rectHV))
            this.right.rangeRecursive(rect, linkedList);
    }

    public Point2D nearest(Point2D p) {
        validateInput(p);
        return this.size == 0 ? null : nearestRecursive(p, this.point2D);
    }

    private Point2D nearestRecursive(Point2D p, Point2D referencePoint) {
        Point2D currentPoint = this.point2D;
        if (currentPoint.distanceSquaredTo(p) > referencePoint.distanceSquaredTo(p)) {
            currentPoint = referencePoint;
        }
        double currentPointDistance = currentPoint.distanceSquaredTo(p);
        if (this.right != null && this.left != null) {
            currentPoint = testLeftAndRightSubTree(p, currentPoint);
        }
        else if (this.left != null &&
                this.left.rectHV.distanceSquaredTo(p) < currentPointDistance) {
            currentPoint = testSubtree(this.left, p, currentPoint);
        }
        else if (this.right != null &&
                this.right.rectHV.distanceSquaredTo(p) < currentPointDistance) {
            currentPoint = testSubtree(this.right, p, currentPoint);
        }
        return currentPoint;
    }

    private Point2D testSubtree(KdTree subtree, Point2D p, Point2D currentPoint) {
        Point2D testPoint = subtree.nearestRecursive(p, currentPoint);
        return currentPoint.distanceSquaredTo(p) < testPoint.distanceSquaredTo(p) ? currentPoint :
               testPoint;
    }

    private Point2D testLeftAndRightSubTree(Point2D p, Point2D currentPoint) {
        double leftRectDistance = this.left.rectHV.distanceSquaredTo(p);
        double rightRectDistance = this.right.rectHV.distanceSquaredTo(p);
        double currentPointDistance = currentPoint.distanceSquaredTo(p);
        if (leftRectDistance < currentPointDistance && leftRectDistance < rightRectDistance) {
            currentPoint = testNearerSubtreeFirst(p, currentPoint, rightRectDistance, this.left,
                                                  this.right);
        }
        else if (rightRectDistance < currentPointDistance) {
            currentPoint = testNearerSubtreeFirst(p, currentPoint, leftRectDistance, this.right,
                                                  this.left);
        }
        return currentPoint;
    }

    private Point2D testNearerSubtreeFirst(Point2D p, Point2D currentPoint,
                                           double otherRectDistance, KdTree first, KdTree last) {
        currentPoint = testSubtree(first, p, currentPoint);
        if (currentPoint.distanceSquaredTo(p) > otherRectDistance) {
            currentPoint = testSubtree(last, p, currentPoint);
        }
        return currentPoint;
    }

    private void validateInput(Object obj) {
        if (obj == null) throw new IllegalArgumentException();
    }

    private static class ToPassTheAutograderTest {
        private static final int a = 0;
    }

    public static void main(String[] args) {
        KdTree kdtree = new KdTree();
        while (!(StdIn.isEmpty())) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            kdtree.insert(new Point2D(x, y));
        }
        System.out.println(kdtree.size());
    }
}
