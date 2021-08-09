import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {

    public static void transform() {
        String inputString = BinaryStdIn.readString();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(inputString);
        StringBuilder burrowsWheelerTransformString = new StringBuilder();
        int first = -1;
        int len = circularSuffixArray.length();
        for (int i = 0; i < len; i++) {
            int charIndex = circularSuffixArray.index(i);
            if (charIndex == 0) {
                first = i;
                burrowsWheelerTransformString.append(inputString.charAt(len - 1));
            }
            else burrowsWheelerTransformString.append(inputString.charAt(charIndex - 1));
        }
        BinaryStdOut.write(first);
        BinaryStdOut.write(burrowsWheelerTransformString.toString());
        BinaryStdOut.close();
    }

    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String lastColumnString = BinaryStdIn.readString();
        int len = lastColumnString.length();
        int[] next = new int[len];
        int[] rIndices = new int[256];
        char[] firstColumn = lastColumnString.toCharArray();
        Arrays.sort(firstColumn);
        rIndices[0] = 0;
        for (int i = 1; i < len; i++) {
            if (firstColumn[i] != firstColumn[i - 1])
                rIndices[(int) firstColumn[i]] = i;
        }
        for (int i = 0; i < len; i++) {
            int rIndex = lastColumnString.charAt(i);
            next[rIndices[rIndex]] = i;
            rIndices[rIndex]++;
        }
        int currentChar = first;
        for (int i = 0; i < len; i++) {
            BinaryStdOut.write(firstColumn[currentChar]);
            currentChar = next[currentChar];
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
    }
}
