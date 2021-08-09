# 8puzzle solver

8puzzle solver using a minimum priority queue of Hamming or Manhattan distances

---

Compilation:  `javac-algs4 Solver.java`

Execution:    `java-algs4 Solver filename.txt`

```
$ cat puzzle04.txt
3
 0  1  3
 4  2  5
 7  8  6

$ java-algs4 Solver puzzle04.txt
Minimum number of moves = 4

3
 0  1  3 
 4  2  5 
 7  8  6 

3
 1  0  3 
 4  2  5 
 7  8  6 

3
 1  2  3 
 4  0  5 
 7  8  6 

3
 1  2  3 
 4  5  0   
 7  8  6 

3
 1  2  3 
 4  5  6 
 7  8  0
```

---

Course-provided java file(s):
* PuzzleChecker.java - can be used to see minimum number of moves required for one or more puzzles. `java-algs4 PuzzleChecker filename1.txt filename2.txt ...`
```
   % java-algs4 PuzzleChecker puzzle*.txt
   puzzle00.txt: 0
   puzzle01.txt: 1
   puzzle02.txt: 2
   puzzle03.txt: 3
   puzzle04.txt: 4
   puzzle05.txt: 5
   puzzle06.txt: 6
   ...
   puzzle3x3-impossible: -1
   ...
   puzzle42.txt: 42
   puzzle43.txt: 43
   puzzle44.txt: 44
   puzzle45.txt: 45
 ```

[Assignment specification](https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php)

