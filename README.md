# Algorithms Part I and Part II

This course covers the essential information that every serious programmer needs to know about algorithms and data structures, with emphasis on applications and scientific performance analysis of Java implementations. It is offered by Princeton University through Coursera. Part I covers elementary data structures, sorting, and searching algorithms. Part II focuses on graph- and string-processing algorithms.

Dependencies: [algs4](https://algs4.cs.princeton.edu/code/)

Compilation and execution commands used in this course are respectively:

`$ javac-algs4 filename.java` :

```
#!/bin/bash

# This must match the install directory
INSTALL=/usr/local/algs4

# Sets the path to the classpath libraries
CLASSPATH=(.:${INSTALL}/algs4.jar)

# Add algs4.jar to classpath and various commmand-line options.
javac -cp "${CLASSPATH}" -g -encoding UTF-8 -Xlint:all -Xlint:-overrides -Xdiags:verbose -Xmaxwarns 10 -Xmaxerrs 10  "$@"
```

`$ java-algs4 filename`:

```
#!/bin/bash

# This must match the install directory.
INSTALL=/usr/local/algs4

# Sets the path to the textbook libraries.
CLASSPATH=(.:${INSTALL}/algs4.jar)

# Execute with textbook libraries in Java classpath.
java -cp "${CLASSPATH}" "$@"
```

Grade received: 100%
based on correctness, memory, and timing.

[Algorithms, Part I](https://www.coursera.org/learn/algorithms-part1)
[Algorithms, Part II](https://www.coursera.org/learn/algorithms-part2?)