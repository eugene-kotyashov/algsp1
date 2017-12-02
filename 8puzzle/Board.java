/******************************************************************************
 *  Name:    Eugene Kotyashov
 *  NetID:   euk
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description: n-puzzle board game class
 *
******************************************************************************/
import java.util.ArrayList;

public class Board {
    private int [][] data;
    private ArrayList<Board> neighborsData;
    private int dim;
    private int manhattanDist;
    private int hammingDist;
    private int zeroRow = -1;
    private int zeroCol = -1;
    
        
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)         
    {
        int goalRow, goalCol;
        int curVal;
        manhattanDist = 0;
        hammingDist = 0;
        neighborsData = null;
        dim = blocks.length;
        data = new int[dim][dim];
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                data[row][col] = blocks[row][col];
                curVal = data[row][col];
                if (curVal == 0) {
                    zeroCol = col;
                    zeroRow = row;
                    continue;
                }
                goalRow = (curVal-1) / dim;
                goalCol = (curVal-1) % dim;
                manhattanDist += 
                    Math.abs(row - goalRow) + Math.abs(col - goalCol); 
                if (data[row][col] != row*dim + col + 1) {
                    hammingDist++;
                }
            }
        }
        //init neighbours
        
    }
    
    public int dimension()                 // board dimension n
    {
        return dim;
    }
    
    public int hamming()                   // number of blocks out of place
    {
        return hammingDist;
        
    }
    // sum of Manhattan distances between blocks and goal
    public int manhattan()                 
    {
        return manhattanDist;
    }
    // is this board the goal board?
    public boolean isGoal()
    {
        return hammingDist == 0;
                
    }
    // a board that is obtained by exchanging any pair of blocks
    public Board twin()
    {
        int [][] tmp = new int [dim][dim];
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                tmp[row][col] = data[row][col];
            }
        }
        
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                if ((row != zeroRow) && (col < dim-1)) {
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
        Board tmp = (Board) y;
        return this.toString().equals(tmp.toString());
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        if (neighborsData == null) {
            neighborsData = new ArrayList<Board>();
            if (zeroCol > 0) {
                data[zeroRow][zeroCol] = data[zeroRow][zeroCol-1];
                data[zeroRow][zeroCol-1] = 0;
                neighborsData.add(new Board(data));
                data[zeroRow][zeroCol-1] = data[zeroRow][zeroCol];
                data[zeroRow][zeroCol] = 0;
            }
            if (zeroCol < dim-1) {
                data[zeroRow][zeroCol] = data[zeroRow][zeroCol+1];
                data[zeroRow][zeroCol+1] = 0;
                neighborsData.add(new Board(data));
                data[zeroRow][zeroCol+1] = data[zeroRow][zeroCol];
                data[zeroRow][zeroCol] = 0;
            }
            if (zeroRow > 0) {
                data[zeroRow][zeroCol] = data[zeroRow-1][zeroCol];
                data[zeroRow-1][zeroCol] = 0;
                neighborsData.add(new Board(data));
                data[zeroRow-1][zeroCol] = data[zeroRow][zeroCol];
                data[zeroRow][zeroCol] = 0;
            }
            if (zeroRow < dim-1) {
                data[zeroRow][zeroCol] = data[zeroRow+1][zeroCol];
                data[zeroRow+1][zeroCol] = 0;
                neighborsData.add(new Board(data));
                data[zeroRow+1][zeroCol] = data[zeroRow][zeroCol];
                data[zeroRow][zeroCol] = 0;
            }            
        }
        return neighborsData;
    }
    
// string representation of this board (in the output format specified below)
    public String toString()             
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