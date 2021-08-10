# Boggle Solver

Boggle solver using DFS and Trie 

[Assignment Specification](https://coursera.cs.princeton.edu/algs4/assignments/boggle/specification.php)

---

Compilation:  `javac-algs4 BoggleSolver.java`

Execution:    `java-algs4 BoggleSolver dictionary.txt board.txt`

*Dictionaries*. A dictionary consists of a sequence of words, separated by whitespace, in alphabetical order. (All uppercase letters only [A-Z])
```
$ cat dictionary-algs4.txt
ABACUS
ABANDON
ABANDONED
ABBREVIATE
...
QUEUE
...
ZOOLOGY

$ cat dictionary-yawl.txt
AA
AAH
AAHED
AAHING
...
PNEUMONOULTRAMICROSCOPICSILICOVOLCANOCONIOSIS
...
ZYZZYVAS
```
*Boards*. A boggle board consists of two integers m and n, followed by the m × n characters in the board, with the integers and characters separated by whitespace. Assumes the integers are nonnegative and that the characters are uppercase letters A through Z (with the two-letter sequence Qu represented as either Q or Qu)
```
$ cat board4x4.txt
4 4
A  T  E  E
A  P  Y  O
T  I  N  U
E  D  S  E

$ cat board-q.txt
4 4
S  N  R  T
O  I  E  L
E  Qu T  T
R  S  A  T
```

```
$ java-algs4 BoggleSolver dictionary-algs4.txt board4x4.txt
AID
DIE
END
ENDS
...
YOU
Score = 33

$ java-algs4 BoggleSolver dictionary-algs4.txt board-q.txt
EQUATION
EQUATIONS
...
QUERIES
QUESTION
QUESTIONS
...
TRIES
Score = 84
```
---
Course provided Java file(s):
* BoggleBoard
* BoggleGame - Boggle game with GUI
