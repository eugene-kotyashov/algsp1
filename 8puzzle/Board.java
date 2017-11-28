
import java.util.Arrays;
import java.util.ArrayList;

public class Board {
    private int [][] data;
    private ArrayList neighborsData;
    private int dim;
    private int manhattanDist;
    private int hammingDist;
    
        
        
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    {
        int goalRow, goalCol;
        int curVal;
        manhattanDist = 0;
        hammingDist = 0;
        dim = blocks.length;
        data = new int[dim][dim];
        for(int row=0; row < dim; row++) {
            for(int col=0; col < dim-1; col++) {
                data[row][col] = blocks[row][col];
                curVal = data[row][col];
                goalRow = curVal / dim;
                goalCol = curVal % dim + 1;
                manhattanDist += Math.abs(goalRow - row) + Math.abs(goalCol-col); 
                if (data[row][col] != row*dim + col + 1){
                    hammingDist++;
                }
            }
        }            
    }
    
    public int dimension()                 // board dimension n
    {
        return dim;
    }
    
    public int hamming()                   // number of blocks out of place
    {
        return hammingDist;
        
    }
    
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        return manhattanDist;
    }
    
    public boolean isGoal()                // is this board the goal board?
    {
        return hammingDist == 0;
                
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int [][] tmp = new int [dim][dim];
        int zeroRow = -1;
        int zeroCol = -1;
        for(int row=0; row < dim; row++) {
            for(int col=0; col < dim; col++) {
                tmp[row][col] = data[row][col];
                if (tmp[row][col] == 0) {
                    zeroRow = row;
                    zeroCol = col;
                }
            }
        }
        
        for(int row=0; row < dim; row++) {
            for(int col=0; col < dim; col++) {
                if ((col != zeroCol) && (col+1 != zeroCol) && (col < dim-2)) {
                    tmp[row][col] = data[row][col+1];
                    tmp[row][col+1] = data[row][col];
                    return new Board(tmp);
                }
            }
        }
        return null;
    }
    
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        int[][] tmp = (int[][])y;
        if (tmp.length != data.length) return false;
        if (tmp.length > 0) {
            if (tmp[0].length != data[0].length)
                return false;
        }
        return Arrays.equals(data, tmp);
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        return neighborsData;
    }
    
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
               s.append(String.format("%2d ", data[i][j]));
        }
        s.append("\n");
    }
    return s.toString();

    }

    public static void main(String[] args) // unit tests (not graded)
    {
    }
}