package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final int size;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF backwashUF;
    private int openSites = 0;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be > 0");
        }
        size = N;
        grid = new boolean[size][size];
        uf = new WeightedQuickUnionUF(size * size + 2);
        backwashUF = new WeightedQuickUnionUF(size * size + 1);  // no virtual bottom site
    }

    public void open(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (!grid[row][col]) {
            grid[row][col] = true;
            openSites++;

            if (row > 0 && isOpen(row - 1, col)) {
                uf.union(index(row, col), index(row - 1, col));
                backwashUF.union(index(row, col), index(row - 1, col));
            }
            if (row < size - 1 && isOpen(row + 1, col)) {
                uf.union(index(row, col), index(row + 1, col));
                backwashUF.union(index(row, col), index(row + 1, col));
            }
            if (col > 0 && isOpen(row, col - 1)) {
                uf.union(index(row, col), index(row, col - 1));
                backwashUF.union(index(row, col), index(row, col - 1));
            }
            if (col < size - 1 && isOpen(row, col + 1)) {
                uf.union(index(row, col), index(row, col + 1));
                backwashUF.union(index(row, col), index(row, col + 1));
            }
            if (row == 0) {
                uf.union(index(row, col), size * size);
                backwashUF.union(index(row, col), size * size);
            }
            if (row == size - 1) {
                uf.union(index(row, col), size * size + 1);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return backwashUF.connected(index(row, col), size * size);  // use backwashUF to avoid backwash
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return uf.connected(size * size, size * size + 1);
    }

    private int index(int row, int col) {
        return row * size + col;
    }
}
