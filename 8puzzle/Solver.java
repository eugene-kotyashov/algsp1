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
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    
    
    private class SearchNode {
        public SearchNode parent;
        public Board bd;
        public int priority;
        public int moves;       

    }
    
    private class PriorityComparator implements Comparator<SearchNode> {
        public int compare(SearchNode s1, SearchNode s2) {
            if (s1.priority < s2.priority) return -1;
            if (s1.priority > s2.priority) return 1;
/*            if (s1.priority == s2.priority) {
                if (s1.bd.hamming() < s2.bd.hamming()) return -1;
                if (s1.bd.hamming() > s2.bd.hamming()) return 1;
            }
*/            return 0;           
        }       
    }
    
    private class GameStep {
        public final ArrayList<Board> processedNodes;
        public SearchNode curNode;
        private MinPQ<SearchNode> pq;        
        
        
        public GameStep(Board brd) {
            pq = new MinPQ<SearchNode>(new PriorityComparator());
            processedNodes = new ArrayList<Board>();
            curNode = new SearchNode();
            curNode.parent = null;
            curNode.bd = brd;            
            curNode.moves = 0;
            curNode.priority = curNode.bd.manhattan() + curNode.moves;
            pq.insert(curNode); 
        }
        
//        private boolean areNeighbors(Board b1, Board b2) {
//            if (Math.abs(b1.manhattan()- b2.manhattan()) == 1) {
//                for (Board n : b2.neighbors()) {
//                    if (b1.equals(n)) return true;
//                }
//            }
//            return false;                    
//        }
        
        public boolean makeStep() {
            curNode = pq.delMin();
            processedNodes.add(curNode.bd);            
            if (curNode.bd.isGoal()) return true;
            for (Board b : curNode.bd.neighbors()) {
                SearchNode neighborNode = new SearchNode();
                neighborNode.parent = curNode;
                neighborNode.bd = b;
                neighborNode.moves = curNode.moves+1;
                neighborNode.priority = 
                    neighborNode.bd.manhattan() + neighborNode.moves;
                boolean isAlreadyProcessed = false;
                //check if a node is already processed 
                for (Board brd : processedNodes) {
                    isAlreadyProcessed = brd.equals(neighborNode.bd);
                    if (isAlreadyProcessed) break;
                }
                //StdOut.println("alreadyProcessed " + isAlreadyProcessed);
                if (!isAlreadyProcessed) 
                    pq.insert(neighborNode);                                   
            }
/*            
            StdOut.println(" dump priority queue \n");
            for (SearchNode node : pq) {
                StdOut.println(node.bd);
                StdOut.println("priority " + node.priority);
                StdOut.println("manhattan " + node.bd.manhattan());
                StdOut.println("moves " + node.moves);
            }
            
            StdOut.println(" dump processed Nodes \n");
            for (Board brd : processedNodes) {
                    StdOut.println(brd);
            }
            StdOut.println("neighbors per step " + i);
*/            
            return false;
        }
    }
    
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

        while (true) {
            //dump priority quieue
//            StdOut.println("======== dumping pq at step "+ 
//                           (step.processedNodes.size()-1));
//            for(SearchNode n : step.pq) { 
//                StdOut.println(n.bd);
//                StdOut.println("manhattan is "+ n.bd.manhattan());
//                StdOut.println("hamming is "+ n.bd.hamming());
//                StdOut.println("moves  "+ n.moves);
//                StdOut.println("priority is " + n.priority);
//            }
            //we are about to make a new game move
            goalFound = step.makeStep();
            if (goalFound) break;
            goalFoundTwin = stepTwin.makeStep();
            if (goalFoundTwin) break;          
        }
    }
    // is the initial board solvable?
    public boolean isSolvable() {
        return goalFound;
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (goalFound) 
            return step.curNode.moves;
        return -1;
        
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!goalFound) return null;
        Stack<Board> result = new Stack<Board>();
        SearchNode node = step.curNode;
        while (node != null) {
            result.push(node.bd);
            node = node.parent;
        }
        return result;
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