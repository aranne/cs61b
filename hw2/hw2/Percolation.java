package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/** Using UnionFind data structure to simulate percolation. */
public class Percolation {
    private boolean[][] table;
    private WeightedQuickUnionUF ufTable;
    private WeightedQuickUnionUF ufTopOnly;
    private int size;
    private int openNumber;

    /** Create N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        // its default value will be false.
        table = new boolean[N][N];
        // Create a UnionFind to track union situations.
        ufTable = new WeightedQuickUnionUF(N * N + 2);
        /* (N*N) site means the virtual top site,
           which connects to all sites in the top row.
         */
        for (int i = 0; i < N; i += 1) {
            ufTable.union(i, N * N);
        }
        /*  (N*N + 1) site means the virtual bottom site,
            which connects to all sites in the bottom row.
         */
        for (int i = N * N - N; i < N * N; i += 1) {
            ufTable.union(i, N * N + 1);
        }
        ufTopOnly = new WeightedQuickUnionUF(N * N + 1);
        /* (N*N) site means the virtual top site,
           which connects to all sites in the top row.
         */
        for (int i = 0; i < N; i += 1) {
            ufTopOnly.union(i, N * N);
        }
        size = N;
        openNumber = 0;
    }

    /** Convert 2-Dimensional coordinate to 1-Dimensional coordinate. */
    private int xyTo1D(int x, int y) {
        return x * size + y;
    }

    /** Check row and col of a site is validate or not. */
    private void validate(int row, int col) {
        if (!(row >= 0 && row < size) || !(col >= 0 && col < size)) {
            throw new IndexOutOfBoundsException();
        }
    }

    /** open the site (row, col) if it is not open already. */
    public void open(int row, int col) {
        validate(row, col);
        if (!isOpen(row, col)) {
            table[row][col] = true;
            openNumber += 1;
            // Connect to neighbor sites.
            int n = xyTo1D(row, col);
            if (row > 0) {
                if (isOpen(row - 1, col)) {
                    ufTable.union(n, xyTo1D(row - 1, col));
                    ufTopOnly.union(n, xyTo1D(row - 1, col));
                }
            }
            if (row < size - 1) {
                if (isOpen(row + 1, col)) {
                    ufTable.union(n, xyTo1D(row + 1, col));
                    ufTopOnly.union(n, xyTo1D(row + 1, col));
                }
            }
            if (col > 0) {
                if (isOpen(row, col - 1)) {
                    ufTable.union(n, xyTo1D(row, col - 1));
                    ufTopOnly.union(n, xyTo1D(row, col - 1));
                }
            }
            if (col < size - 1) {
                if (isOpen(row, col + 1)) {
                    ufTable.union(n, xyTo1D(row, col + 1));
                    ufTopOnly.union(n, xyTo1D(row, col + 1));
                }
            }
        }
    }

    /** Return true if the site (row, col) is open. */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return table[row][col];
    }

    /** Return true if the site (row, col) is full. */
    public boolean isFull(int row, int col) {
        validate(row, col);
        /* When use ufTopOnly to indicate whether this site is connected to the virtual top site.
        Because if we use ufTable, there will be backwash. What is backwash?
        When the system is already percolated and we open some other sites from the bottom,
        these sites will be full because
        the virtual top site, virtual bottom site and these sites are all connected.
        However, these sites shouldn't be full,
        so we remove the virtual bottom site (using ufTopOnly) to avoid backwash.
         */
        return isOpen(row, col) && ufTopOnly.connected(xyTo1D(row, col), size * size);
    }

    /** Returns the number of opened sites. */
    public int numberOfOpenSites() {
        return openNumber;
    }

    /** Returns true if the system percolates. */
    public boolean percolates() {
        return ufTable.connected(size * size, size * size + 1);
    }




    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(2, 2);
        p.open(0, 1);

        System.out.println("#### Test isOpen()");
        System.out.println(p.isOpen(2, 2));
        System.out.println(p.isOpen(0, 2));

        System.out.println("#### Test isFull()");
        p.open(1, 1);
        p.open(1, 2);
        p.open(0, 3);
        System.out.println(p.isFull(0, 3));
        System.out.println(p.isFull(2, 2));

        System.out.println("### Test numberOfOpenSites()");
        System.out.println(p.numberOfOpenSites());

        System.out.println("### Test percolates()");
        System.out.println(p.percolates());
        p.open(3, 2);
        System.out.println(p.percolates());

        System.out.println("### Test backwash");
        p.open(3, 0);
        System.out.println(p.isFull(3, 0));
    }
}
