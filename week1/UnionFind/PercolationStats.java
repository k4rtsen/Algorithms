import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StopwatchCPU;

public class PercolationStats {
    private Percolation p;
    private double[] xt;
    private int times;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Illegal Argument Exception");
        times = trials;
        xt = new double[times];
        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            int openedSites = 0;
            while (!p.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                    openedSites++;
                }
            }
            xt[i] = (double) openedSites / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(xt);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(xt);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return StdStats.mean(xt) - 1.96 * StdStats.stddev(xt) / Math.sqrt(times);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return StdStats.mean(xt) + 1.96 * StdStats.stddev(xt) / Math.sqrt(times);
    }

    public static void main(String[] args) {
        int n = 20, t = 30;
        if (args.length == 2){
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        }
        StopwatchCPU timer = new StopwatchCPU();
        PercolationStats ps = new PercolationStats(n, t);
        double time = timer.elapsedTime();
        StdOut.printf("%f seconds\n", time);
        StdOut.printf("mean\t\t\t= %f\n", ps.mean());
        StdOut.printf("stddev\t\t\t= %f\n", ps.stddev());
        StdOut.println("95% confidence interval\t= [" + String.format("%.3f", ps.confidenceLo()) + ", "
                + String.format("%.3f", ps.confidenceHi()) + "]");
    }
}
