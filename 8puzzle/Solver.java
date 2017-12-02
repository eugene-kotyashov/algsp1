/******************************************************************************
 *  Name:    Eugene Kotyashov
 *  NetID:   euk
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: class for solving n-puzzle game with A* search algorithm
 * using minimum priority queue 
 *
******************************************************************************/
import java.util.Comparator;
import java.util.ArrayList;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    
    
    private class SearchNode {
        
        public Board bd;
        public int priority;

    }
    
    private class PriorityComparator implements Comparator<SearchNode> {
        
        public int compare(SearchNode s1, SearchNode s2) {
            if (s1.priority < s2.priority) return -1;
            if (s1.priority > s2.priority) return 1;
            return 0;
        }       
    }
    
    private class GameStep {
        private MinPQ<SearchNode> pq;        
        public ArrayList<Board> processedNodes;
        
        public GameStep(Board brd) {
            pq = new MinPQ<SearchNode>(new PriorityComparator());
            processedNodes = new ArrayList<Board>();
            SearchNode start = new SearchNode();
            start.bd = brd;
            start.priority = 0 + start.bd.manhattan();
            pq.insert(start); 
        }
        
        public boolean makeStep() {
            SearchNode curNode = pq.delMin();
            processedNodes.add(curNode.bd);            
            if (curNode.bd.isGoal()) return true;
            for (Board b : curNode.bd.neighbors()) {
                SearchNode neighborNode = new SearchNode();
                neighborNode.bd = b;
                neighborNode.priority = b.manhattan() + totalMoves;
                boolean isAlreadyProcessed = false;
                //check if a node is already processed 
                for (Board brd : processedNodes) {
                    isAlreadyProcessed = brd.equals(neighborNode.bd);
                    if (isAlreadyProcessed) break;
                }
                if (!isAlreadyProcessed) 
                    pq.insert(neighborNode);               
                
            }
            return false;
        }
    }
    
    private int totalMoves;
    private boolean goalFound;
    private boolean goalFoundTwin;
    private GameStep step;
    private GameStep stepTwin;
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.IllegalArgumentException();
        step = new GameStep(initial);
        stepTwin = new GameStep(initial.twin());
        totalMoves = 0;   

        while (true) {
            /*StdOut.println("pririty quieue trace");
            for(SearchNode sn : pq) {
                StdOut.println(sn.bd);
                StdOut.println("manhattan is "+sn.bd.manhattan());
                StdOut.println("priority is "+ sn.priority);
            }
            */
            //dequeue an item with minimal priority
            goalFound = step.makeStep();
            if (goalFound) break;
            goalFoundTwin = stepTwin.makeStep();
            if (goalFoundTwin) break;
            //we are about to make a new game move
            totalMoves++;
            
            
        }
    }
    // is the initial board solvable?
    public boolean isSolvable() {
        return goalFound;
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return totalMoves;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return step.processedNodes;
    }
    // solve a slider puzzle (given below)
    public static void main(String[] args) 
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}