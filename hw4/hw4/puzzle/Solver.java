package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.List;
import java.util.ArrayList;

public class Solver {
    /** Each SearchNode is a WorldState, represents one move sequences,
     * and has a reference to the previous search node.
     */
    private class SearchNode implements Comparable<SearchNode> {
        private int moves;
        private SearchNode parent;
        private WorldState worldState;
        private int estimatedDistanceToGoal;
        public SearchNode(WorldState ws, SearchNode p) {
            if (p == null) {
                moves = 0;
            } else {
                moves = p.moves + 1;
            }
            parent = p;
            worldState = ws;
            // To cache the estimated distance to goal as an instance variable.
            estimatedDistanceToGoal = ws.estimatedDistanceToGoal();
        }
        public int compareTo(SearchNode sn) {
            int thisTotal = this.moves + this.estimatedDistanceToGoal;
            int thatTotal = sn.moves + sn.estimatedDistanceToGoal;
            return thisTotal - thatTotal;
        }
    }

    private WorldState start;
    private SearchNode finalNode;

    /**
     * Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists!
     */
    public Solver(WorldState initial) {
        start = initial;
        SearchNode sn = new SearchNode(initial, null);
        finalNode = BestFirstSearch(sn);
    }

    private SearchNode BestFirstSearch(SearchNode sn) {
        MinPQ<SearchNode> pq = new MinPQ<>();
        SearchNode node = sn;
        while (!node.worldState.isGoal()) {
            for (WorldState ws : node.worldState.neighbors()) {
                // Do not inqueue the parent's WorldState.
                if (node.parent == null || !ws.equals(node.parent.worldState)) {
                    pq.insert(new SearchNode(ws, node));
                }
            }
            node = pq.delMin();
        }
        return node;
    }

    /** Returns the minimum number of moves to solve the puzzle starting at the initial WorldState. */
    public int moves() {
        return finalNode.moves;
    }

    /** Returns a sequence of WorldStates from the initial WorldState to the solution. */
    public Iterable<WorldState> solution() {
        List<WorldState> solution = new ArrayList<>();
        SearchNode sn = finalNode;
        while (!sn.worldState.equals(start)) {
            solution.add(0, sn.worldState);
            sn = sn.parent;
        }
        solution.add(0, sn.worldState);
        return solution;
    }

}