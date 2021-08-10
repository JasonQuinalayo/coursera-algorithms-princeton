# Burrows-Wheeler Compression

Transforms an input of bytes using the Burrows-Wheeler Transform algorithm into a format that lends well to the Huffman Compression algorithm 

[Assignment Specification](https://coursera.cs.princeton.edu/algs4/assignments/burrows/specification.php)

---

Compilation:
```
$ javac-algs4 BurrowsWheeler.java
$ javac-algs4 MoveToFront.java
$ javac-algs4 CircularSuffixArray.java
```

Compression:
```
$ java-algs4 BurrowsWheeler - < inputfilename | java-algs4 MoveToFront - | java-algs4 edu.princeton.cs.algs4.Huffman - > outputfilename
```

Decompression:
```
$ java-algs4 edu.princeton.cs.algs4.Huffman + < inputfilename | java-algs4 MoveToFront + | java-algs4 BurrowsWheeler + > outputfilename
```

---
