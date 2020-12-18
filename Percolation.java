package percolationSimulation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] sitesStatus;
    private int openSites = 0;
    private final WeightedQuickUnionUF wquf;
    private final static boolean OPEN = true;
    private final int N;

    private int indexOfSite(int row, int col) {
        return ((row * this.N) + col + 1);
    }

    private void initializeTopAndBottomSurfaces() {
        for (int i = 1; i <= this.N; ++i) {
            this.wquf.union(0, i);
            this.wquf.union(((this.N * this.N) - 1), ((this.N * this.N) - 1) - i);
        }
    }

    private void connectToOpenNeighbours(int row, int col) {
        int site = indexOfSite(row, col);

        if (row > 0 && isOpen(row - 1, col)) {
            this.wquf.union(site, indexOfSite(row - 1, col));
        }

        if (row < this.N - 1 && isOpen(row + 1, col)) {
            this.wquf.union(site, indexOfSite(row + 1, col));
        }

        if (col > 0 && isOpen(row, col - 1)) {
            this.wquf.union(site, indexOfSite(row, col - 1));
        }

        if (col < this.N - 1 && isOpen(row, col + 1)) {
            this.wquf.union(site, indexOfSite(row, col + 1));
        }
    }

    public Percolation(int n) {
        this.N = n;
        this.wquf = new WeightedQuickUnionUF((n * n) + 2);
        this.sitesStatus = new boolean[n][n];
        initializeTopAndBottomSurfaces();
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            this.sitesStatus[row][col] = OPEN;
            this.openSites++;
            connectToOpenNeighbours(row, col);
        }
    }

    public boolean isOpen(int row, int col) {
        return this.sitesStatus[row][col];
    }

//    public boolean isFull(int row, int col) {
//        return (this.wquf.find(0) == this.wquf.find(indexOfSite(row, col));
//    }

    public int numberOfOpenSites() {
        return this.openSites;
    }

    public boolean percolates() {
        return (this.wquf.find(0) == this.wquf.find(this.N - 1));
    }
}
