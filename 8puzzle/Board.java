import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class Board {
    private final int[][] tiles;
    private final int dimension;
    private int rowEmpty;
    private int columnEmpty;

    public Board(int[][] tiles) {
        dimension = tiles.length;
        this.tiles = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == 0) {
                    rowEmpty = i;
                    columnEmpty = j;
                }
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(Integer.toString(dimension));
        stringBuilder.append("\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                stringBuilder.append(tiles[i][j]);
                stringBuilder.append(' ');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        int numOfTileInWrongPos = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != (i * dimension) + j + 1) {
                    numOfTileInWrongPos++;
                }
            }
        }
        return numOfTileInWrongPos;
    }

    public int manhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] != 0) {
                    manhattanDistance += distanceFromOriginalPosition(tiles[i][j], i, j);
                }
            }
        }
        return manhattanDistance;
    }

    private int distanceFromOriginalPosition(int value, int row, int column) {
        return Math.abs(((value - 1) / dimension) - row) +
                Math.abs(((value - 1) % dimension) - column);
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();
        if (rowEmpty + 1 < dimension)
            neighbors.push(swapValues(rowEmpty + 1, columnEmpty, rowEmpty, columnEmpty));
        if (columnEmpty + 1 < dimension)
            neighbors.push(swapValues(rowEmpty, columnEmpty + 1, rowEmpty, columnEmpty));
        if (columnEmpty - 1 >= 0)
            neighbors.push(swapValues(rowEmpty, columnEmpty - 1, rowEmpty, columnEmpty));
        if (rowEmpty - 1 >= 0)
            neighbors.push(swapValues(rowEmpty - 1, columnEmpty, rowEmpty, columnEmpty));
        return neighbors;
    }

    public Board twin() {
        if (tiles[0][0] == 0) {
            return swapValues(0, 1, 1, 1);
        }
        else if (tiles[0][1] == 0) {
            return swapValues(0, 0, 1, 0);
        }
        else {
            return swapValues(0, 0, 0, 1);
        }
    }

    private Board swapValues(int aRow, int aColumn, int bRow, int bColumn) {
        Board newBoard = new Board(tiles);
        if (tiles[aRow][aColumn] == 0) {
            newBoard.rowEmpty = bRow;
            newBoard.columnEmpty = bColumn;
        }
        else if (tiles[bRow][bColumn] == 0) {
            newBoard.rowEmpty = aRow;
            newBoard.columnEmpty = aColumn;
        }
        newBoard.tiles[aRow][aColumn] = tiles[bRow][bColumn];
        newBoard.tiles[bRow][bColumn] = tiles[aRow][aColumn];
        return newBoard;
    }

    public static void main(String[] args) {

    }
}

