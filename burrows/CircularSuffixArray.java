import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private final String originalString;
    private final CircularSuffix[] circularSuffixes;
    private final int length;

    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException();
        originalString = s;
        length = s.length();
        circularSuffixes = new CircularSuffix[length];
        for (int i = 0; i < length; i++) {
            circularSuffixes[i] = new CircularSuffix(i);
        }
        // Arrays.sort(circularSuffixes);
        CircularSuffixMSD.sort(circularSuffixes);
    }

    private class CircularSuffix {
        private final int initialCharIndex;

        private CircularSuffix(int initialCharIndex) {
            this.initialCharIndex = initialCharIndex;
        }

        private char charAt(int i) {
            if (i >= length) throw new IllegalArgumentException("ithChar out of bounds");
            int offset = i + initialCharIndex;
            if (offset >= length) return originalString.charAt(offset - length);
            else return originalString.charAt(offset);
        }

        private int length() {
            return length;
        }

        private int compare(CircularSuffix c, int i) {
            if (i == length) return 0; // one character string(?)
            int diff = Character.compare(this.charAt(i), c.charAt(i));
            if (diff == 0) {
                return compare(c, i + 1);
            }
            else return diff;
        }

        public int compareTo(CircularSuffix c) {
            return compare(c, 0);
        }
    }

    // Copy, pasted, and modified MSD sort from Princeton algs4.jar
    // Significantly faster than 3 way quicksort and Java Timsort.
    private static class CircularSuffixMSD {
        private static final int BITS_PER_BYTE = 8;
        private static final int BITS_PER_INT = 32;   // each Java int is 32 bits
        private static final int R = 256;   // extended ASCII alphabet size
        private static final int CUTOFF = 15;   // cutoff to insertion sort

        /**
         * Rearranges the array of extended ASCII strings in ascending order.
         *
         * @param a the array to be sorted
         */
        public static void sort(CircularSuffix[] a) {
            int n = a.length;
            CircularSuffix[] aux = new CircularSuffix[n];
            sort(a, 0, n - 1, 0, aux);
        }

        // return dth character of s, -1 if d = length of string
        private static int charAt(CircularSuffix s, int d) {
            assert d >= 0 && d <= s.length();
            if (d == s.length()) return -1;
            return s.charAt(d);
        }

        // sort from a[lo] to a[hi], starting at the dth character
        private static void sort(CircularSuffix[] a, int lo, int hi, int d, CircularSuffix[] aux) {

            // cutoff to insertion sort for small subarrays
            if (hi <= lo + CUTOFF) {
                insertion(a, lo, hi, d);
                return;
            }

            // compute frequency counts
            int[] count = new int[R + 2];
            for (int i = lo; i <= hi; i++) {
                int c = charAt(a[i], d);
                count[c + 2]++;
            }

            // transform counts to indicies
            for (int r = 0; r < R + 1; r++)
                count[r + 1] += count[r];

            // distribute
            for (int i = lo; i <= hi; i++) {
                int c = charAt(a[i], d);
                aux[count[c + 1]++] = a[i];
            }

            // copy back
            for (int i = lo; i <= hi; i++)
                a[i] = aux[i - lo];


            // recursively sort for each character (excludes sentinel -1)
            for (int r = 0; r < R; r++)
                sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
        }


        // insertion sort a[lo..hi], starting at dth character
        private static void insertion(CircularSuffix[] a, int lo, int hi, int d) {
            for (int i = lo; i <= hi; i++)
                for (int j = i; j > lo && less(a[j], a[j - 1], d); j--)
                    exch(a, j, j - 1);
        }

        // exchange a[i] and a[j]
        private static void exch(CircularSuffix[] a, int i, int j) {
            CircularSuffix temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }

        // is v less than w, starting at character d
        private static boolean less(CircularSuffix v, CircularSuffix w, int d) {
            // assert v.substring(0, d).equals(w.substring(0, d));
            for (int i = d; i < Math.min(v.length(), w.length()); i++) {
                if (v.charAt(i) < w.charAt(i)) return true;
                if (v.charAt(i) > w.charAt(i)) return false;
            }
            return v.length() < w.length();
        }


        /**
         * Rearranges the array of 32-bit integers in ascending order.
         * Currently assumes that the integers are nonnegative.
         *
         * @param a the array to be sorted
         */
        public static void sort(int[] a) {
            int n = a.length;
            int[] aux = new int[n];
            sort(a, 0, n - 1, 0, aux);
        }

        // MSD sort from a[lo] to a[hi], starting at the dth byte
        private static void sort(int[] a, int lo, int hi, int d, int[] aux) {

            // cutoff to insertion sort for small subarrays
            if (hi <= lo + CUTOFF) {
                insertion(a, lo, hi, d);
                return;
            }

            // compute frequency counts (need R = 256)
            int[] count = new int[R + 1];
            int mask = R - 1;   // 0xFF;
            int shift = BITS_PER_INT - BITS_PER_BYTE * d - BITS_PER_BYTE;
            for (int i = lo; i <= hi; i++) {
                int c = (a[i] >> shift) & mask;
                count[c + 1]++;
            }

            // transform counts to indicies
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];

            // distribute
            for (int i = lo; i <= hi; i++) {
                int c = (a[i] >> shift) & mask;
                aux[count[c]++] = a[i];
            }

            // copy back
            for (int i = lo; i <= hi; i++)
                a[i] = aux[i - lo];

            // no more bits
            if (d == 4) return;

            // recursively sort for each character
            if (count[0] > 0)
                sort(a, lo, lo + count[0] - 1, d + 1, aux);
            for (int r = 0; r < R; r++)
                if (count[r + 1] > count[r])
                    sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
        }

        // insertion sort a[lo..hi], starting at dth character
        private static void insertion(int[] a, int lo, int hi, int d) {
            for (int i = lo; i <= hi; i++)
                for (int j = i; j > lo && a[j] < a[j - 1]; j--)
                    exch(a, j, j - 1);
        }

        // exchange a[i] and a[j]
        private static void exch(int[] a, int i, int j) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    public int length() {
        return length;
    }

    public int index(int i) {
        if (i < 0 || i >= length) throw new IllegalArgumentException();
        return circularSuffixes[i].initialCharIndex;
    }

    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(s);
        StdOut.println("Length: " + circularSuffixArray.length());
        for (int i = 0; i < circularSuffixArray.length(); i++) {
            StdOut.println("index[" + i + "]: " + circularSuffixArray.index(i));
        }
    }
}
