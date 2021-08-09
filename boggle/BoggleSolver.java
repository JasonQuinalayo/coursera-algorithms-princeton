import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class BoggleSolver {
    private final TST[][] tstArray = new TST[26][26];
    private final HashMap<String, Integer> scoreCache = new HashMap<>();

    public BoggleSolver(String[] dictionary) {
        if (dictionary.length > 100000) {  // Rough TST Balancing
            int divisions = dictionary.length / 1352;
            int first = 0;
            int second = divisions;
            while (first < dictionary.length) {
                int i;
                if (second <= dictionary.length) {
                    i = second - 1;
                }
                else {
                    i = dictionary.length - 1;
                }
                for (; i >= first; i--) {
                    putToTSTArray(dictionary[i]);
                }
                first += divisions;
                second += divisions;
            }
        }
        else for (String str : dictionary) putToTSTArray(str);
    }

    private void putToTSTArray(String word) {
        if (word.length() <= 2) {
            return;
        }
        int firstIndex = word.charAt(0) - 'A';
        int secondIndex = word.charAt(1) - 'A';
        if (tstArray[firstIndex][secondIndex] == null)
            tstArray[firstIndex][secondIndex] = TST.initializeTST(word);
        else tstArray[firstIndex][secondIndex].put(word);
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> words = new TreeSet<>();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                DFS dfs = new DFS(i, j, board, words);
            }
        }
        return words;
    }

    public int scoreOf(String word) {
        if (word.length() < 3) return 0;
        Integer score = scoreCache.get(word);
        if (score == null) {
            int firstIndex = word.charAt(0) - 'A';
            int secondIndex = word.charAt(1) - 'A';
            if (tstArray[firstIndex][secondIndex] == null) return 0;
            else {
                return tstArray[firstIndex][secondIndex].get(word);
            }
        }
        else return score;
    }

    private class DFS {
        private final int rows;
        private final int cols;
        private final BoggleBoard board;
        private final boolean[][] currentPath;
        private final Set<String> words;
        private final StringBuilder stringBuilder = new StringBuilder();

        private DFS(int i, int j, BoggleBoard board, Set<String> words) {
            this.words = words;
            this.board = board;
            rows = board.rows();
            cols = board.cols();
            currentPath = new boolean[rows][cols];
            dfs(i, j, null);
        }

        private void dfs(int i, int j, TST currentNode) {
            currentPath[i][j] = true;
            char letter = board.getLetter(i, j);
            if (letter == 'Q') stringBuilder.append("QU");
            else stringBuilder.append(letter);
            if (stringBuilder.length() < 2) {
                currentNode = null;
            }
            else if (stringBuilder.length() == 2 || (stringBuilder.length() == 3
                    && stringBuilder.charAt(1) == 'Q')) {
                int firstIndex = stringBuilder.charAt(0) - 'A';
                int secondIndex = stringBuilder.charAt(1) - 'A';
                if (tstArray[firstIndex][secondIndex] == null) {
                    revertPath(i, j, letter);
                    return;
                }
                currentNode = tstArray[firstIndex][secondIndex].middle;
                if (stringBuilder.charAt(1) == 'Q')
                    currentNode = currentNode == null ? null : matchNode('U', currentNode.middle);
            }
            else {
                currentNode = matchNode(letter, currentNode.middle);
                if (letter == 'Q')
                    currentNode = currentNode == null ? null : matchNode('U', currentNode.middle);
            }
            if (stringBuilder.length() > 2) {
                if (currentNode == null) {
                    revertPath(i, j, letter);
                    return;
                }
                if (currentNode.value != 0) {
                    String string = stringBuilder.toString();
                    scoreCache.put(string, currentNode.value);
                    words.add(string);
                }
            }
            iterateNeighbors(i, j, currentNode);
            revertPath(i, j, letter);
        }

        private void revertPath(int i, int j, char letter) {
            if (letter == 'Q')
                stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            else stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            currentPath[i][j] = false;
        }

        private void iterateNeighbors(int i, int j, TST currentNode) {
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    if (i + di >= 0 && i + di < rows && j + dj >= 0 && j + dj < cols &&
                            !currentPath[i + di][j + dj]) {
                        dfs(i + di, j + dj, currentNode);
                    }
                }
            }
        }

        private TST matchNode(char key, TST currentNode) {
            if (currentNode == null) return null;
            if (key < currentNode.key) return matchNode(key, currentNode.left);
            else if (key > currentNode.key) return matchNode(key, currentNode.right);
            else return currentNode;
        }
    }

    /* A slightly modified version of TST.java found in algs4.jar */
    private static class TST {
        private TST left = null;
        private TST right = null;
        private TST middle = null;
        private char key;
        private int value = 0;

        private static TST initializeTST(String word) {
            TST tst = new TST();
            tst.key = word.charAt(0);
            tst.middle = tst.put(tst.middle, word, 1, getWordValue(word));
            return tst;
        }

        private void put(String word) {
            int wordValue = getWordValue(word);
            put(this, word, 0, wordValue);
        }

        private static int getWordValue(String word) {
            int len = word.length();
            int wordValue;
            if (len <= 4) wordValue = 1;
            else if (len == 5) wordValue = 2;
            else if (len == 6) wordValue = 3;
            else if (len == 7) wordValue = 5;
            else wordValue = 11;
            return wordValue;
        }

        private TST put(TST node, String word, int i, int wordValue) {
            char c = word.charAt(i);
            if (node == null) {
                node = new TST();
                node.key = c;
            }
            if (c < node.key) node.left = put(node.left, word, i, wordValue);
            else if (c > node.key) node.right = put(node.right, word, i, wordValue);
            else if (i < word.length() - 1) node.middle = put(node.middle, word, i + 1, wordValue);
            else node.value = wordValue;
            return node;
        }

        public int get(String word) {
            if (word == null) {
                throw new IllegalArgumentException("calls get() with null argument");
            }
            if (word.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
            int x = get(this, word, 0);
            return x;
        }

        private int get(TST x, String word, int d) {
            if (x == null) return 0;
            if (word.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
            char c = word.charAt(d);
            if (c < x.key) return get(x.left, word, d);
            else if (c > x.key) return get(x.right, word, d);
            else if (d < word.length() - 1) return get(x.middle, word, d + 1);
            else return x.value;
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
