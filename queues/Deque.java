import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    public Deque() {

    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    private void validateArgument(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
    }

    public void addFirst(Item item) {
        validateArgument(item);
        Node nodeToAdd = new Node(item, first, null);
        if (isEmpty()) {
            first = nodeToAdd;
            last = nodeToAdd;
        }
        else {
            first.previous = nodeToAdd;
            first = nodeToAdd;
        }
        size++;
    }

    public void addLast(Item item) {
        validateArgument(item);
        Node nodeToAdd = new Node(item, null, last);
        if (isEmpty()) {
            first = nodeToAdd;
            last = nodeToAdd;
        }
        else {
            last.next = nodeToAdd;
            last = nodeToAdd;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item returnValue = first.value;
        first = first.next;
        if (first == null) {
            last = null;
            size--;
            return returnValue;
        }
        first.previous = null;
        size--;
        return returnValue;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item returnValue = last.value;
        last = last.previous;
        if (last == null) {
            first = null;
            size--;
            return returnValue;
        }
        last.next = null;
        size--;
        return returnValue;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!(hasNext())) {
                throw new NoSuchElementException();
            }
            Item item = current.value;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        private Item value = null;
        private Node next = null;
        private Node previous = null;

        public Node(Item value, Node next, Node previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        System.out.println(deque);
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());
        deque.addFirst(1321);
        deque.addLast(123);
        deque.addFirst(239);
        deque.addLast(2149);
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());
        for (Integer i : deque)
            System.out.println(i);
    }
}
