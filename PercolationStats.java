package percolationSimulation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] openSitesFractions;
    private final int trials;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        openSitesFractions = new double[trials];
        this.trials = trials;
    }

    public double mean() {
        return StdStats.mean(this.openSitesFractions);
    }

    public double stddev() {
        return StdStats.stddev(this.openSitesFractions);
    }

    public double confidenceLo() {
        return (mean() + ((1.96 * stddev()) / Math.sqrt(trials)));
    }

    public double confidenceHi() {
        return (mean() - ((1.96 * stddev()) / Math.sqrt(trials)));
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("you should enter N and T as a arguments");
            return;
        }

        int N = Integer.parseInt(String.valueOf(args[0]));
        int T = Integer.parseInt(String.valueOf(args[1]));
        PercolationStats percolationStatsModule = new PercolationStats(N, T);
        Percolation percolationModule = new Percolation(N);

        for (int i = 0; i < T; ++i) {
            while (!percolationModule.percolates()) {
                percolationModule.open(StdRandom.uniform(N), StdRandom.uniform(N));
            }
            percolationStatsModule.openSitesFractions[i] = percolationModule.numberOfOpenSites();
        }

        StdOut.print(percolationStatsModule.mean());
        StdOut.print(percolationStatsModule.stddev());
        StdOut.println(percolationStatsModule.confidenceLo() + " " + percolationStatsModule.confidenceHi());
    }
}
