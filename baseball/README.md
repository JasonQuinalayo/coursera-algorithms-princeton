# Baseball Elimination

Baseball Elimination using the Ford-Fulkerson maximum flow algorithm.

[Assignment Specification](https://coursera.cs.princeton.edu/algs4/assignments/baseball/specification.php)

---

Compilation:  `javac-algs4 BaseballElimination.java`

Execution:    `java-algs4 BaseballElimination filename.txt`

**Input format**:   The input format is the number of teams in the division n followed by one line for each team. Each line contains the team name (with no internal whitespace characters), the number of wins, the number of losses, the number of remaining games, and the number of remaining games against each team in the division.

```
$ cat teams4.txt
4
Atlanta       83 71  8  0 1 6 1
Philadelphia  80 79  3  1 0 0 2
New_York      78 78  6  6 0 0 0
Montreal      77 82  3  1 2 0 0

$ cat teams5.txt
5
New_York    75 59 28   0 3 8 7 3
Baltimore   71 63 28   3 0 2 7 7
Boston      69 66 27   8 2 0 0 3
Toronto     63 72 27   7 7 0 0 3
Detroit     49 86 27   3 7 3 3 0
```

```
$ java-algs4 BaseballElimination teams4.txt
Atlanta is not eliminated
Philadelphia is eliminated by the subset R = { Atlanta New_York }
New_York is not eliminated
Montreal is eliminated by the subset R = { Atlanta }

$ java-algs4 BaseballElimination teams5.txt
New_York is not eliminated
Baltimore is not eliminated
Boston is not eliminated
Toronto is not eliminated
Detroit is eliminated by the subset R = { New_York Baltimore Boston Toronto }
```

---
