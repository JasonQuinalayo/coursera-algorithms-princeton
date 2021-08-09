import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;

public class MoveToFront {

    public static void encode() {
        LinkedList<Character> asciiSequence = MoveToFront.createAsciiSequence();
        while (!(BinaryStdIn.isEmpty())) {
            char c = BinaryStdIn.readChar();
            int index = asciiSequence.indexOf(c);
            asciiSequence.remove(index);
            asciiSequence.addFirst(c);
            BinaryStdOut.write((char) index);
        }
        BinaryStdOut.close();
    }

    public static void decode() {
        LinkedList<Character> asciiSequence = MoveToFront.createAsciiSequence();
        while (!(BinaryStdIn.isEmpty())) {
            int index = BinaryStdIn.readChar();
            char c = asciiSequence.get(index);
            asciiSequence.remove(index);
            asciiSequence.addFirst(c);
            BinaryStdOut.write(c);
        }
        BinaryStdOut.close();
    }

    private static LinkedList<Character> createAsciiSequence() {
        LinkedList<Character> asciiSequence = new LinkedList<>();
        for (char i = 0; i < 256; i++) {
            asciiSequence.add(i);
        }
        return asciiSequence;
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) MoveToFront.encode();
        else if (args[0].equals("+")) MoveToFront.decode();
    }

}
