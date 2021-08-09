import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int capacity = 2;
    private int firstEmptySlotPointer = 0;

    public RandomizedQueue() {
        arr = (Item[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return firstEmptySlotPointer == 0;
    }

    public int size() {
        return firstEmptySlotPointer;
    }

    private void increaseCapacity() {
        capacity += capacity;
        copyToNewArr();
    }

    private void copyToNewArr() {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size(); i++) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    private void decreaseCapacity() {
        capacity = capacity / 2;
        copyToNewArr();
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (firstEmptySlotPointer == capacity) {
            increaseCapacity();
        }
        arr[firstEmptySlotPointer++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randIndex = StdRandom.uniform(firstEmptySlotPointer);
        Item item = arr[randIndex];
        arr[randIndex] = arr[firstEmptySlotPointer - 1];
        firstEmptySlotPointer--;
        arr[firstEmptySlotPointer] = null;
        if (capacity > 4 && firstEmptySlotPointer == capacity / 4) {
            decreaseCapacity();
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return arr[StdRandom.uniform(firstEmptySlotPointer)];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final RandomizedQueue<Item> randomizedQueue = new RandomizedQueue<Item>();

        public RandomizedQueueIterator() {
            for (int i = 0; i < size(); i++) {
                randomizedQueue.enqueue(arr[i]);
            }
        }

        public boolean hasNext() {
            return !(randomizedQueue.isEmpty());
        }

        public Item next() {
            if (!(hasNext())) {
                throw new NoSuchElementException();
            }
            return randomizedQueue.dequeue();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
        System.out.println("Adding 10 random numbers");
        for (int i = 0; i < 10; i++) {
            int random = StdRandom.uniform(10);
            randomizedQueue.enqueue(random);
            System.out.println(random);
        }
        System.out.println("Size, Emptiness:");
        System.out.println(randomizedQueue.size());
        System.out.println(randomizedQueue.isEmpty());
        System.out.println("Using Foreach to print:");
        for (Integer i : randomizedQueue)
            System.out.println(i);
        System.out.println("Printing sample but not dequeue");
        for (int i = 0; i < 10; i++) {
            System.out.println(randomizedQueue.sample());
        }
        System.out.println("Dequeue:");
        for (int i = 0; i < 10; i++) {
            System.out.println(randomizedQueue.dequeue());
        }
        System.out.println("Size, Emptiness:");
        System.out.println(randomizedQueue.size());
        System.out.println(randomizedQueue.isEmpty());
    }
}
