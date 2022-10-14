import edu.princeton.cs.algs4.WeightedQuickUnionUF;

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
        if (n <= 0)
            throw new IllegalArgumentException();
        uf = new WeightedQuickUnionUF(n * n + 2);
        grid = new boolean[n][n];
        bottom = n * n + 1;
        for (var i = 0; i < grid.length; i++)
            for (var j = 0; j < grid[i].length; j++)
                grid[i][j] = false;
    }

    // trash
    // ----------------------------------------------------
    /**
     * Opens the site {@code grid[row][col]} if it is not open already
     * 
     * @param row - row in grid, where {@code 0 <= row < grid.length}
     * @param col - column in grid, where {@code 0 <= col < grid.length}
     * 
     * @throws IllegalArgumentException if {@code 1 < row || row > grid.length 
     * || 1 < col || col > grid.length}
     */
    public void open(int row, int col) {
        row--;
        col--;
        validate(row, col);
        open0(row, col);
    }

    /**
     * Is the site {@code grid[row][col]} open?
     * 
     * @param row - row in grid, where {@code 0 <= row < grid.length}
     * @param col - column in grid, where {@code 0 <= col < grid.length}
     * @return {@code true} if {@code grid[row][col]} is open, {@code false}
     *         otherwise
     * 
     * @throws IllegalArgumentException if {@code 1 < row || row > grid.length 
     * || 1 < col || col > grid.length}
     */
    public boolean isOpen(int row, int col) {
        row--;
        col--;
        validate(row, col);
        return isOpen0(row, col);
    }

    /**
     * Is site connect with any sites in first row
     * 
     * @param row - row in grid, where {@code 0 <= row < grid.length}
     * @param col - column in grid, where {@code 0 <= col < grid.length}
     * @return {@code true} if {@code uf.connected(grid[row][col], top)}, {@code false} otherwise
     * 
     * @throws IllegalArgumentException if {@code 1 < row || row > grid.length 
     *      || 1 < col || col > grid.length}
     */
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
    
    private void open0(int row, int col) {
        grid[row][col] = true;
        unoinOpenSites(row, col);
    }

    private boolean isOpen0(int row, int col) {
        return grid[row][col];
    }

    private boolean isFull0(int row, int col) {
        if (uf.find(fromGridToUF(row, col)) == uf.find(top))
            return true;
        return false;
    }

    /**
     * Сounts the number of open cells
     * 
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        int count = 0;
        for (boolean[] subArr : grid)
            for (boolean b : subArr)
                if (b)
                    count++;
        return count;
    }

    /**
     * Вoes the system percolate?
     * 
     * @return {@code true} if any sites in the lower row is connected to any sites in the upper row
     */
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
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
}
