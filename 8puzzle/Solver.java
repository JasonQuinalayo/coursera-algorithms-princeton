import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode finalNode;
    private boolean solvable;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        final MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        final MinPQ<SearchNode> pqtwin = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(initial, 0, null));
        pqtwin.insert(new SearchNode(initial.twin(), 0, null));
        while (true) {
            if (solve(pq)) {
                solvable = true;
                break;
            }
            if (solve(pqtwin)) {
                solvable = false;
                break;
            }
        }
    }

    private boolean solve(MinPQ<SearchNode> pq) {
        SearchNode min = pq.delMin();
        if (min.board.isGoal()) {
            finalNode = min;
            return true;
        }
        for (Board board : min.board.neighbors()) {
            if (min.previousSearchNode == null ||
                    !(board.equals(min.previousSearchNode.board))) {
                pq.insert(new SearchNode(board, min.numOfMoves + 1, min));
            }
        }
        return false;
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (solvable) return finalNode.numOfMoves;
        else return -1;
    }

    public Iterable<Board> solution() {
        if (solvable) {
            Stack<Board> solution = new Stack<Board>();
            SearchNode currentNode = finalNode;
            do {
                solution.push(currentNode.board);
                currentNode = currentNode.previousSearchNode;
            } while (currentNode != null);
            return solution;
        }
        else {
            return null;
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int numOfMoves;
        private final int priority;
        private final SearchNode previousSearchNode;

        public SearchNode(Board board, int numOfMoves, SearchNode searchNode) {
            this.board = board;
            this.numOfMoves = numOfMoves;
            this.previousSearchNode = searchNode;
            this.priority = board.manhattan() + numOfMoves;
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(priority, that.priority);
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
