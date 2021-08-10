# KdTree

A data type that represents a set of points in the unit square (all points have x- and y-coordinates between 0 and 1) using a 2d-tree to support efficient range search (find all of the points contained in a query rectangle) and nearest-neighbor search (find a closest point to a query point)

[Assignment Specification](https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php)

---

API
```
public class KdTree {
   public KdTree()                              // construct an empty set of points 
   public boolean isEmpty()                     // is the set empty? 
   public int size()                            // number of points in the set 
   public void insert(Point2D p)                // add the point to the set (if it is not already in the set)
   public boolean contains(Point2D p)           // does the set contain point p? 
   public void draw()                           // draw all points to standard draw 
   public Iterable<Point2D> range(RectHV rect)  // all points that are inside the rectangle (or on the boundary) 
   public Point2D nearest(Point2D p)            // a nearest neighbor in the set to point p; null if the set is empty 

   public static void main(String[] args)       // unit testing of the methods 
}
```

---

Course provided Java file(s):
* RangeSearchVisualizer.java
* NearestNeighborVisualizer.java
* KdTreeVisualizer.java
* KdTreeGenerator.java
