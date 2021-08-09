import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private int[][] pixelsRGB;
    private double[][] energyArray;
    private int width;
    private int height;
    private boolean transposed = false;

    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();
        width = picture.width();
        height = picture.height();
        pixelsRGB = new int[width][height];
        fillPixelsRGB(picture);
        energyArray = new double[width][height];
        fillEnergyArray();
    }

    private void fillPixelsRGB(Picture picture) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixelsRGB[i][j] = picture.getRGB(i, j);
            }
        }
    }

    private void fillEnergyArray() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                energyArray[i][j] = computeEnergy(i, j);
            }
        }
    }

    private double computeEnergy(int column, int row) {
        if (row == 0 || column == 0 || row == height - 1 || column == width - 1) return 1000;
        return Math.sqrt(energyX(column, row) + energyY(column, row));
    }

    private double energyX(int column, int row) {
        int[] leftRGB = rgb(pixelsRGB[column - 1][row]);
        int[] rightRGB = rgb(pixelsRGB[column + 1][row]);
        return sumOfSquaredDifferences(leftRGB, rightRGB);
    }

    private double energyY(int column, int row) {
        int[] aboveRGB = rgb(pixelsRGB[column][row - 1]);
        int[] belowRGB = rgb(pixelsRGB[column][row + 1]);
        return sumOfSquaredDifferences(aboveRGB, belowRGB);
    }

    private int[] rgb(int rgb) {
        int[] rgbArr = {
                (rgb >> 16) & 0xFF,
                (rgb >> 8) & 0xFF,
                (rgb) & 0xFF
        };
        return rgbArr;
    }

    private double sumOfSquaredDifferences(int[] a, int[] b) {
        return Math.pow(a[0] - b[0], 2) +
                Math.pow(a[1] - b[1], 2) +
                Math.pow(a[2] - b[2], 2);
    }

    public Picture picture() {
        if (transposed) transpose();
        Picture picture = new Picture(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                picture.setRGB(i, j, pixelsRGB[i][j]);
            }
        }
        return picture;
    }

    public int width() {
        return transposed ? height : width;
    }

    public int height() {
        return transposed ? width : height;
    }

    public double energy(int x, int y) {
        if (x >= (transposed ? height : width) || x < 0 || y >= (transposed ? width : height)
                || y < 0)
            throw new IllegalArgumentException();
        return transposed ? energyArray[y][x] : energyArray[x][y];
    }

    public int[] findHorizontalSeam() {
        if (!transposed) transpose();
        return findSeam();
    }

    public int[] findVerticalSeam() {
        if (transposed) transpose();
        return findSeam();
    }

    private int[] findSeam() {
        MinimumSeam minimumSeam = new MinimumSeam();
        return minimumSeam.seam();
    }

    private class MinimumSeam {
        private final double[][] distanceTo = new double[width][height];
        private final int[][] edgeTo = new int[width][height];
        private int minDistColumn;
        private double minDistance = Double.POSITIVE_INFINITY;

        private MinimumSeam() {
            for (int i = 0; i < width; i++) {
                distanceTo[i][0] = 1000;
            }
            for (int j = 0; j < height - 1; j++) {
                for (int i = 0; i < width; i++) {
                    relaxEdges(i, j);
                }
            }
            updateMinDistance();
        }

        private void relaxEdges(int i, int j) {
            if (i - 1 >= 0) {
                updatePixelPath(i, j, i - 1, j + 1);
            }
            if (i + 1 < width) {
                updatePixelPath(i, j, i + 1, j + 1);
            }
            updatePixelPath(i, j, i, j + 1);
        }

        private void updatePixelPath(int iFrom, int jFrom, int iTo, int jTo) {
            double newDistance = energyArray[iTo][jTo] + distanceTo[iFrom][jFrom];
            if (distanceTo[iTo][jTo] == 0 || distanceTo[iTo][jTo] > newDistance) {
                edgeTo[iTo][jTo] = iFrom;
                distanceTo[iTo][jTo] = newDistance;
            }
        }

        private void updateMinDistance() {
            for (int i = 0; i < width; i++) {
                if (distanceTo[i][height - 1] < minDistance) {
                    minDistance = distanceTo[i][height - 1];
                    minDistColumn = i;
                }
            }
        }

        public int[] seam() {
            int[] seam = new int[height];
            seam[height - 1] = minDistColumn;
            int currentColumn = minDistColumn;
            for (int i = height - 1; i > 1; i--) {
                currentColumn = edgeTo[currentColumn][i];
                seam[i - 1] = currentColumn;
            }
            seam[0] = currentColumn;
            return seam;
        }
    }

    public void removeHorizontalSeam(int[] seam) {
        if (!transposed) transpose();
        removeSeam(seam);
    }

    public void removeVerticalSeam(int[] seam) {
        if (transposed) transpose();
        removeSeam(seam);
    }

    private void removeSeam(int[] seam) {
        validateSeamRemoval(seam);
        shiftPixels(seam);
        computeNewEnergyArray(seam);
        if (width < pixelsRGB.length / 1.5) {
            resizeArrays();
        }
    }

    private void validateSeamRemoval(int[] seam) {
        if (seam == null || seam.length != height || width == 1)
            throw new IllegalArgumentException();
        for (int i = 0; i < seam.length; i++) {
            if ((i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1) ||
                    (i < seam.length - 1 && Math.abs(seam[i] - seam[i + 1]) > 1) ||
                    seam[i] < 0 || seam[i] >= width) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void shiftPixels(int[] seam) {
        for (int j = 0; j < height; j++) {
            for (int i = seam[j]; i < width - 1; i++) {
                pixelsRGB[i][j] = pixelsRGB[i + 1][j];
                energyArray[i][j] = energyArray[i + 1][j];
            }
        }
        width -= 1;
    }

    private void computeNewEnergyArray(int[] seam) {
        for (int j = 0; j < height; j++) {
            int column = seam[j];
            if (column - 1 >= 0) energyArray[column - 1][j] = computeEnergy(column - 1, j);
            if (column < width) energyArray[column][j] = computeEnergy(column, j);
        }
    }

    private void resizeArrays() {
        int[][] newPixelsRGB = new int[width][height];
        double[][] newEnergyArray = new double[width][height];
        for (int i = 0; i < width; i++) {
            System.arraycopy(pixelsRGB[i], 0, newPixelsRGB[i], 0, height);
            System.arraycopy(energyArray[i], 0, newEnergyArray[i], 0, height);
        }
        pixelsRGB = newPixelsRGB;
        energyArray = newEnergyArray;
    }

    private void transpose() {
        int[][] newPixelsRGB = new int[height][width];
        double[][] newEnergyArray = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                newPixelsRGB[i][j] = pixelsRGB[j][i];
                newEnergyArray[i][j] = energyArray[j][i];
            }
        }
        transposed = !transposed;
        int temp = width;
        width = height;
        height = temp;
        pixelsRGB = newPixelsRGB;
        energyArray = newEnergyArray;
    }

    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        SeamCarver seamCarver = new SeamCarver(picture);
        int vertical = Integer.parseInt(args[1]);
        int horizontal = Integer.parseInt(args[2]);
        for (int i = 0; i < vertical; i++) {
            seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        }
        for (int i = 0; i < horizontal; i++) {
            seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        }
        seamCarver.picture().save(args[3]);
    }
}
