package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private Percolation p;
    private double[] data;
    private int times;

    /** Perform T independent experiments on an N-by-N grid. */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        data = new double[T];
        times = T;
        // Experiment T times.
        for (int i = 0; i < T; i += 1) {
            p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            data[i] = (double) p.numberOfOpenSites() / (N * N);
        }
    }

    /** Returns the sample mean of percolation threshold. */
    public double mean() {
        return StdStats.mean(data);
    }

    /** Returns the sample standard deviation of percolation threshold. */
    public double stddev() {
        return StdStats.stddev(data);
    }

    /** Returns the low endpoint of 95% confidence interval. */
    public double confidenceLow() {
        return mean() - 1.96 * Math.sqrt(stddev() / times);
    }

    /** Returns the high endpoint of 95% confidence interval. */
    public double confidenceHigh() {
        return mean() + 1.96 * Math.sqrt(stddev() / times);
    }

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(800, 50, pf);
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLow());
        System.out.println(ps.confidenceHigh());
    }
}
