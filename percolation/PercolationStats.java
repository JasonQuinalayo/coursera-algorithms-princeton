import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] fractionsOfOpenSites;

    public PercolationStats(int n, int trials) {
        Percolation percolation;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        fractionsOfOpenSites = new double[trials];
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            int size = n * n;
            while (!(percolation.percolates())) {
                int cellToBeOpened = StdRandom.uniform(size);
                int cellRow = (cellToBeOpened / n) + 1;
                int cellColumn = (cellToBeOpened % n) + 1;
                if (!(percolation.isOpen(cellRow, cellColumn))) {
                    percolation.open(cellRow, cellColumn);
                }
            }
            fractionsOfOpenSites[i] = (double) percolation.numberOfOpenSites() / size;
        }
    }

    public double mean() {
        return StdStats.mean(fractionsOfOpenSites);
    }

    public double stddev() {
        return StdStats.stddev(fractionsOfOpenSites);
    }

    public double confidenceLo() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(fractionsOfOpenSites.length));
    }

    public double confidenceHi() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(fractionsOfOpenSites.length));
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                                                      Integer.parseInt(args[1]));
        System.out.println("mean = " + Double.toString(stats.mean()));
        System.out.println("stddev = " + Double.toString(stats.stddev()));
        System.out
                .println(
                        "95% confidence interval = [" + Double.toString(stats.confidenceLo()) + ", "
                                + Double.toString(stats.confidenceHi()) + "]");
    }
}
