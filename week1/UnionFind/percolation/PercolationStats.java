import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] xt;
    private int times;

    /**
     * Perform independent trials on an n-by-n grid
     * 
     * @param n grid size
     * @param trials count of experiments
     * 
     * @throws IllegalArgumentException if {@code n <= 0 or trials <= 0}
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        times = trials;
        xt = new double[times];
        for (int i = 0; i < times; i++) {
            Percolation p = new Percolation(n);
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

    /**
     * @return sample mean of percolation threshold
     */
    public double mean() {
        return StdStats.mean(xt);
    }

    /**
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(xt);
    }

    /**
     * @return low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(times);
    }

    /**
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(times);
    }

    // test client (see below)
    public static void main(String[] args){
        int n = 20, t = 30;
        if (args.length == 2){
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        }
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.println("mean\t\t\t= " + ps.mean());
        StdOut.println("stddev\t\t\t= " + ps.stddev());
        StdOut.println("95% confidence interval\t= [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
