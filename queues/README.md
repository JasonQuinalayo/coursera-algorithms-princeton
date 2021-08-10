# Queues

[Assignment Specification](https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php)

### Deque
A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue that supports adding and removing items from either the front or the back of the data structure
```
public class Deque<Item> implements Iterable<Item> {

    // construct an empty deque
    public Deque()

    // is the deque empty?
    public boolean isEmpty()

    // return the number of items on the deque
    public int size()

    // add the item to the front
    public void addFirst(Item item)

    // add the item to the back
    public void addLast(Item item)

    // remove and return the item from the front
    public Item removeFirst()

    // remove and return the item from the back
    public Item removeLast()

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()

    // unit testing 
    public static void main(String[] args)

}
```

### Randomized Queue
A data structure similar to a stack or queue, except that the item removed is chosen uniformly at random among items in the data structure 

```
public class RandomizedQueue<Item> implements Iterable<Item> {

    // construct an empty randomized queue
    public RandomizedQueue()

    // is the randomized queue empty?
    public boolean isEmpty()

    // return the number of items on the randomized queue
    public int size()

    // add the item
    public void enqueue(Item item)

    // remove and return a random item
    public Item dequeue()

    // return a random item (but do not remove it)
    public Item sample()

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()

    // unit testing 
    public static void main(String[] args)

}
```

### Permutation
Takes an integer k as a command-line argument; reads a sequence of strings from standard input using StdIn.readString(); and prints exactly k of them, uniformly at random

Compilation: `javac-algs4 Permutation.java`

Execution:: `java-algs4 Permutation k < filename.txt`

```
$ cat distinct.txt
A B C D E F G H I

$ cat duplicates.txt
AA BB BB BB BB BB CC CC

$ java Permutation 8 < duplicates.txt
BB
AA
BB
CC
BB
BB
CC
BB

$ java Permutation 3 < distinct.txt
C
G
A

$ java Permutation 3 < distinct.txt
E
F
G
```

---
