# Percolation

Given a composite systems comprised of randomly distributed insulating and metallic materials: what fraction of the materials need to be metallic so that the composite system is an electrical conductor? Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an abstract process known as *percolation* to model such situations.

[Assignment Specification](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php)

----

### PercolationStats
Estimates the percolation threshold N through a Monte-Carlo simulation

Compilation: `javac-algs4 PercolationStats.java`

Execution: `java-algs4 PercolationStats n T` 

where: 

n = dimension of grid 

T = number of trials.

```
$ java-algs4 PercolationStats 200 100
mean                    = 0.5929934999999997
stddev                  = 0.00876990421552567
95% confidence interval = [0.5912745987737567, 0.5947124012262428]

$ java-algs4 PercolationStats 200 100
mean                    = 0.592877
stddev                  = 0.009990523717073799
95% confidence interval = [0.5909188573514536, 0.5948351426485464]

$ java-algs4 PercolationStats 2 10000
mean                    = 0.666925
stddev                  = 0.11776536521033558
95% confidence interval = [0.6646167988418774, 0.6692332011581226]

$ java-algs4 PercolationStats 2 100000
mean                    = 0.6669475
stddev                  = 0.11775205263262094
95% confidence interval = [0.666217665216461, 0.6676773347835391]
```

---

Course provided Java file(s):
* InteractivePercolationVisualizer.java
* PercolationVisualizer.java
