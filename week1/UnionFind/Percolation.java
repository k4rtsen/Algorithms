import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StopwatchCPU;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private boolean[][] grid;
    private int top, bottom;

    {
        top = 0;
    }

    /**
     * Creates {@code n*n} grid, with all sites initially blocked
     * 
     * @param n the size of grid
     * 
     * @throws IllegalArgumentException if {@code n <= 0}
     */
    public Percolation(int n) {
        try {
            if (n <= 0)
                throw new IllegalArgumentException("The size of the argument must be greater than 0");
            uf = new WeightedQuickUnionUF(n * n + 2);
            grid = new boolean[n][n];
            bottom = n * n + 1;
            for (int i = 0; i < grid.length; i++)
                for (int j = 0; j < grid[i].length; j++)
                    grid[i][j] = false;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // trash
    // ----------------------------------------------------
    public void open(int row, int col) {
        row--;
        col--;
        validate(row, col);
        open0(row, col);
    }

    public boolean isOpen(int row, int col) {
        row--;
        col--;
        validate(row, col);
        return isOpen0(row, col);
    }

    public boolean isFull(int row, int col) {
        row--;
        col--;
        validate(row, col);
        return isFull0(row, col);
    }
    // ----------------------------------------------------

    private void validate(int row, int col) {
        if (0 > row || row >= grid.length || 0 > col || col >= grid.length)
            throw new IllegalArgumentException();
    }

    /**
     * Opens the site {@code grid[row][col]} if it is not open already
     * 
     * @param row - row in grid, where {@code 0 <= row < grid.length}
     * @param col - column in grid, where {@code 0 <= col < grid.length}
     * 
     * @throws IllegalArgumentException if {@code 0 < row || row >= grid.length 
     * || 0 < col || col >= grid.length}
     */
    public void open0(int row, int col) {
        try {
            if (0 > row || row >= grid.length || 0 > col || col >= grid.length)
                throw new IllegalArgumentException("Out of range in open");
            grid[row][col] = true;
            unoinOpenSites(row, col);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return;
        }
    }

    /**
     * Is the site {@code grid[row][col]} open?
     * 
     * @param row - row in grid, where {@code 0 <= row < grid.length}
     * @param col - column in grid, where {@code 0 <= col < grid.length}
     * @return {@code true} if {@code grid[row][col]} is open, {@code false}
     *         otherwise
     * 
     * @throws IllegalArgumentException if {@code 0 < row || row >= grid.length 
     * || 0 < col || col >= grid.length}
     */
    public boolean isOpen0(int row, int col) {
        try {
            if (0 > row || row >= grid.length || 0 > col || col >= grid.length)
                throw new IllegalArgumentException("Out of range in isOpen");
            return grid[row][col];
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    // is the site (row, col) full?
    // is the site connected with any sites on top row?
    public boolean isFull0(int row, int col) {
        try {
            if (0 > row || row >= grid.length || 0 > col || col >= grid.length)
                throw new IllegalArgumentException("Out of range in isFull");
            if (uf.find(fromGridToUF(row, col)) == uf.find(top))
                return true;
            return false;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (boolean[] sub_arr : grid)
            for (boolean b : sub_arr)
                if (b)
                    count++;
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    public void printGrid() {
        for (boolean[] sub_arr : grid) {
            System.out.print("{ ");
            for (boolean b : sub_arr) {
                if (b)
                    System.out.print(". ");
                else
                    System.out.print("o ");
            }
            System.out.print("}\n");
        }
    }

    private int fromGridToUF(int row, int col) {
        return row * grid.length + col + 1;
    }

    /**
     * if
     * the site is not located in the top, down, left or right borders ->
     * check the neighbour:
     * if it open -> do union operation
     */
    private void unoinOpenSites(int row, int col) {
        if (row == 0)
            uf.union(fromGridToUF(row, col), top);
        if (row == grid.length - 1)
            uf.union(fromGridToUF(row, col), bottom);

        if (row != 0 && isOpen0(row - 1, col))
            uf.union(fromGridToUF(row, col), fromGridToUF(row - 1, col));

        if (row != grid.length - 1 && isOpen0(row + 1, col))
            uf.union(fromGridToUF(row, col), fromGridToUF(row + 1, col));

        if (col != 0 && isOpen0(row, col - 1))
            uf.union(fromGridToUF(row, col), fromGridToUF(row, col - 1));

        if (col != grid.length - 1 && isOpen0(row, col + 1))
            uf.union(fromGridToUF(row, col), fromGridToUF(row, col + 1));
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation p = new Percolation(n);
        /**
         * for random experiment
         */
        StopwatchCPU timer = new StopwatchCPU();
        while (!p.percolates()) {
            int row = StdRandom.uniformInt(n);
            int col = StdRandom.uniformInt(n);
            if (!p.isOpen0(row, col))
                p.open0(row, col);
        }
        var time = timer.elapsedTime();

        /**
         * for input.txt experiment
         */
        // while (!StdIn.isEmpty()) {
        //     int row = StdIn.readInt();
        //     int col = StdIn.readInt();
        //     if (!p.isOpen(row, col))
        //         p.open(row, col);
        // }

        p.printGrid();
        StdOut.printf("%f seconds\n", time);
        System.out.println("Open sites: " + p.numberOfOpenSites());
    }
}
