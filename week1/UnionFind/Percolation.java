import edu.princeton.cs.algs4.*;

interface Operation {
    void connect(int x, int y, int x1, int y1);
}

public class Percolation {
    private UnionFind uf;
    private boolean[][] grid;

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
                throw new IllegalArgumentException("Out of range");
            uf = new UnionFind(n * n);
            grid = new boolean[n][n];
            for (int i = 0; i < grid.length; i++)
                for (int j = 0; j < grid[i].length; j++)
                    grid[i][j] = false;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return;
        }
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
    public void open(int row, int col) {
        try {
            if (0 > row || row >= grid.length || 0 > col || col >= grid.length)
                throw new IllegalArgumentException("Out of range");
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
     * @return {@code true} if {@code grid[row][col]} is open, {@code false} otherwise
     * 
     * @throws IllegalArgumentException if {@code 0 < row || row >= grid.length 
     * || 0 < col || col >= grid.length}
     */
    public boolean isOpen(int row, int col) {
        try {
            if (0 > row || row >= grid.length || 0 > col || col >= grid.length)
                throw new IllegalArgumentException("Out of range");
            return grid[row][col];
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    // is the site (row, col) full?
    // is the site connected with any sites on top row?
    public boolean isFull(int row, int col) {
        try {
            if (0 > row || row >= grid.length || 0 > col || col >= grid.length)
                throw new IllegalArgumentException("Out of range");
            for (int i = 0; i < grid.length; i++) {
                int p = fromGridToUF(row, col);
                // the condition check with each sites in the top row
                if (uf.connected(p, i))
                    return true;
            }
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
        for (int i = 0; i < grid.length; i++)
            if (isFull(grid.length - 1, i))
                return true;
        return false;
    }

    public void printGrid() {
        for (boolean[] sub_arr : grid) {
            System.out.printf("{ ");
            for (boolean b : sub_arr) {
                if (b)
                    System.out.printf("o");
                else
                    System.out.printf("x");
            }
            System.out.printf(" }");
            System.out.println();
        }
    }

    private int fromGridToUF(int row, int col) {
        return row * grid.length + col;
    }

    /**
     * if
     * the site is not located in the top, down, left or right borders ->
     * check the neighbour:
     * if it open -> do union operation
     */
    private void unoinOpenSites(int row, int col) {
        Operation op = (x, y, x1, y1) -> {
            int self = fromGridToUF(x, y);
            int neighbour = fromGridToUF(x1, y1);
            if (!uf.connected(self, neighbour))
                uf.union(self, neighbour);
        };

        if (row != 0)
            if (isOpen(row - 1, col))
                op.connect(row, col, row - 1, col);

        if (row != grid.length - 1)
            if (isOpen(row + 1, col)) {
                op.connect(row, col, row + 1, col);
            }

        if (col != 0)
            if (isOpen(row, col - 1))
                op.connect(row, col, row, col - 1);

        if (col != grid.length - 1)
            if (isOpen(row, col + 1))
                op.connect(row, col, row, col + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        final int N = 5;
        Percolation p = new Percolation(N);

        int i = 0;
        while (!p.percolates()) {
            i++;
            int row = StdRandom.uniformInt(N);
            int col = StdRandom.uniformInt(N);
            if (!p.isOpen(row, col))
                p.open(row, col);
                System.out.printf("Row: %d\tCol: %d\n", row, col);
        }
        System.out.println("iterations: " + i);
        System.out.println("Count of open sites: " + p.numberOfOpenSites());
        p.printGrid();
    }
}
