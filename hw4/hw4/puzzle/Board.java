package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

/**
 * The method neighbors() in this class is to get the neighbors of a Board.
 * This was adapted from a open solution from Josh Hug on 20180401 on course CS61b of UC Berkeley:
 * http://joshh.ug/neighbors.html
 */
public class Board implements WorldState {
    private int[][] board;
    private int size;
    private static final int BLANK = 0;

    /** Constructs a board from an N-by-N array of tiles
     * where tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] tiles) {
        size = tiles.length;
        board = new int[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                board[i][j] = tiles[i][j];
            }
        }
    }

    /** Returns value of tile at row i, column j (or 0 if blank) */
    public int tileAt(int i, int j) {
        if (!(i >= 0 && i < size) || !(j >= 0 && j < size)) {
            throw new IndexOutOfBoundsException("Invalid Index: " + i + " or " + j);
        }
        return board[i][j];
    }

    /** Returns the board size N. */
    public int size() {
        return size;
    }

    /** Returns the neighbors of the current board. */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        // To find the BLANK coordinate.
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        // Copy a new Board.
        int[][] copy = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                copy[pug][yug] = tileAt(pug, yug);
            }
        }
        // Find the neighbors.
        for (int x = 0; x < hug; x++) {
            for (int y = 0; y < hug; y++) {
                // If the distance is 1, then this is a neighbor.
                if (Math.abs(x - bug) + Math.abs(y - zug) == 1) {
                    copy[bug][zug] = copy[x][y];
                    copy[x][y] = BLANK;
                    Board neighbor = new Board(copy);
                    neighbors.enqueue(neighbor);
                    copy[x][y] = copy[bug][zug];
                    copy[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /**
     * There're two goal distance estimates.
     * Hamming estimate: The number of tiles in the wrong position.
     * Manhattan estimate: The sum of the Manhattan distances
     * (sum of the vertical and horizontal distance) from the tiles
     * to their goal positions.
     */

    /** Hamming estimate described below. */
    public int hamming() {
        int wrongPos = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if (isWrongPos(board[i][j], i, j)) {
                    wrongPos += 1;
                }
            }
        }
        return wrongPos;
    }
    private boolean isWrongPos(int tile, int y, int x) {
        int xx, yy;
        if (tile == BLANK) {
            return false;
        } else {
            xx = (tile - 1) % size;
            yy = (tile - 1) / size;
        }
        return x != xx || y != yy;
    }

    /** Manhattan estimate described below. */
    public int manhattan() {
        int totalDis = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                totalDis += manhattanDistance(board[i][j], i, j);
            }
        }
        return totalDis;
    }
    private int manhattanDistance(int tile, int y, int x) {
        int xx, yy;
        if (tile == BLANK) {
            return 0;
        } else {
            xx = (tile - 1) % size;
            yy = (tile - 1) / size;
        }
        return Math.abs(x - xx) + Math.abs(y - yy);
    }

    /** Estimated distance to goal. This method should
     * simply return the results of manhattan() when submitted to
     * Gradescope.
     */
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /** Returns true if this board's tile values are the same position as y's. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        Board b = (Board) o;

        if (this.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if (this.tileAt(i, j) != b.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return estimatedDistanceToGoal();
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
