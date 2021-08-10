# Seam Carving

A content-aware image resizing technique where the image is reduced in size by one pixel of height (or width) at a time

[Assignment Specification](https://coursera.cs.princeton.edu/algs4/assignments/seam/specification.php)

---

```
public class SeamCarver {

   // create a seam carver object based on the given picture
   public SeamCarver(Picture picture)

   // current picture
   public Picture picture()

   // width of current picture
   public int width()

   // height of current picture
   public int height()

   // energy of pixel at column x and row y
   public double energy(int x, int y)

   // sequence of indices for horizontal seam
   public int[] findHorizontalSeam()

   // sequence of indices for vertical seam
   public int[] findVerticalSeam()

   // remove horizontal seam from current picture
   public void removeHorizontalSeam(int[] seam)

   // remove vertical seam from current picture
   public void removeVerticalSeam(int[] seam)

   //  unit testing
   public static void main(String[] args)

}
```

Optionally:
```
$ javac-algs4 SeamCarver.java
$ java-algs4 SeamCarver input.png x y output.png
```
where:

x = number of vertical seams to be removed

y = number of horizontal seams to be removed

(May not throw exceptions)

---

Course provided Java file(s):
* PrintEnergy.java
* PrintSeams.java
* ResizeDemo.java
* SCUtility.java
* ShowEnergy.java
* ShowSeams.java
