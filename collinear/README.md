# Collinear Points

Given a set of n distinct points in the plane, finds every (maximal) line segment that connects a subset of 4 or more of the points.

[Assignment Specification](https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php)

---

Compilation: `javac-algs4 FastCollinearPoints.java`

Execution: `java-algs4 FastCollinearPoints filename.txt`

```
$ cat input6.txt        $ cat input8.txt
6                       8
19000  10000             10000      0
18000  10000                 0  10000
32000  10000              3000   7000
21000  10000              7000   3000
 1234   5678             20000  21000
14000  10000              3000   4000
                         14000  15000
                          6000   7000
```

```
$ java-algs4 FastCollinearPoints input8.txt
(3000, 4000) -> (20000, 21000) 
(0, 10000) -> (10000, 0)

$ java-algs4 FastCollinearPoints input6.txt
(14000, 10000) -> (32000, 10000) 

```

---

Course provided Java file(s):
* Point.java
* LineSegment.java
