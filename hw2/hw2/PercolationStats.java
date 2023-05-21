package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] thresholds;
    private static final double CONFIDENCE_95 = 1.96;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException("N and T must be > 0");

        thresholds = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation perc = pf.make(N);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                perc.open(row, col);
            }
            thresholds[i] = (double) perc.numberOfOpenSites() / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLow() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(thresholds.length);
    }

    public double confidenceHigh() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(thresholds.length);
    }
}
