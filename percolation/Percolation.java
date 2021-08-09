import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf, ufWithoutVirtualBottom;
    private final int dimension;
    private final int size;
    private boolean[] cellStates;
    private int numOfOpenSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        dimension = n;
        size = n * n;
        uf = new WeightedQuickUnionUF(size + 2);
        ufWithoutVirtualBottom = new WeightedQuickUnionUF(size + 1);
        cellStates = new boolean[size + 2];
        cellStates[0] = true;
        cellStates[size + 1] = true;
        numOfOpenSites = 0;
    }

    private void validateRowAndColumn(int row, int column) {
        if (row < 1 || row > dimension || column < 1 || column > dimension) {
            throw new IllegalArgumentException();
        }
    }

    private int map2DTo1D(int row, int column) {
        return (row - 1) * dimension + column;
    }

    public void open(int row, int column) {
        validateRowAndColumn(row, column);
        if (isOpen(row, column)) {
            return;
        }
        int cellIndex = map2DTo1D(row, column);
        cellStates[cellIndex] = true;
        numOfOpenSites++;
        connectToNeighbors(cellIndex);
    }

    private void connectToNeighbors(int cellIndex) {
        connectTop(cellIndex);
        connectBottom(cellIndex);
        connectRight(cellIndex);
        connectLeft(cellIndex);
    }

    private void connectTop(int cellIndex) {
        if (cellIndex <= dimension) {
            uf.union(0, cellIndex);
            ufWithoutVirtualBottom.union(0, cellIndex);
        }
        else if (cellStates[cellIndex - dimension]) {
            uf.union(cellIndex, cellIndex - dimension);
            ufWithoutVirtualBottom.union(cellIndex, cellIndex - dimension);
        }

    }

    private void connectBottom(int cellIndex) {
        if (cellIndex > size - dimension) {
            uf.union(cellIndex, size + 1);
        }
        else if (cellStates[cellIndex + dimension]) {
            uf.union(cellIndex, cellIndex + dimension);
            ufWithoutVirtualBottom.union(cellIndex, cellIndex + dimension);
        }

    }

    private void connectLeft(int cellIndex) {
        if ((cellIndex - 1) % dimension != 0 && cellStates[cellIndex - 1]) {
            uf.union(cellIndex, cellIndex - 1);
            ufWithoutVirtualBottom.union(cellIndex, cellIndex - 1);
        }

    }

    private void connectRight(int cellIndex) {
        if (cellIndex % dimension != 0 && cellStates[cellIndex + 1]) {
            uf.union(cellIndex, cellIndex + 1);
            ufWithoutVirtualBottom.union(cellIndex, cellIndex + 1);
        }
    }

    public boolean isOpen(int row, int column) {
        validateRowAndColumn(row, column);
        return cellStates[map2DTo1D(row, column)];
    }

    public boolean isFull(int row, int column) {
        validateRowAndColumn(row, column);
        return isOpen(row, column) && ufWithoutVirtualBottom.find(0) == ufWithoutVirtualBottom
                .find(map2DTo1D(row, column));
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    public boolean percolates() {
        return uf.find(0) == uf.find(size + 1);
    }

    public static void main(String[] args) {
        int dimensions = StdIn.readInt();
        Percolation test = new Percolation(dimensions);
        while (!(StdIn.isEmpty())) {
            int row = StdIn.readInt();
            int column = StdIn.readInt();
            test.open(row, column);
        }
    }
}
