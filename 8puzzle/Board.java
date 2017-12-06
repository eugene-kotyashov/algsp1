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
    private int dim;
    private int manhattanDist;
    private int hammingDist;
    private int zeroRow = -1;
    private int zeroCol = -1;
    
       
        
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)         
    {
        int curVal;
        int rowDiff, colDiff;
        manhattanDist = 0;
        hammingDist = 0;
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
                rowDiff = Math.abs(row - goalRow(row, col));
                colDiff = Math.abs(col - goalCol(row, col));
                manhattanDist += rowDiff + colDiff; 
                    //(row - goalRow) + (col - goalCol);
                if (data[row][col] != row*dim + col + 1) {
                    hammingDist++;
                }
            }
        }
        //check for linear conflict
        /*for (int row = 0; row < dim-1; row++) {
            for (int col = 0; col < dim-1; col++) {
                if ((data[row][col] == 0) || (data[row+1][col] == 0))
                    continue;
                if ( (goalCol(row,col)==goalCol(row+1,col)) &&
                    ( goalRow(row,col)-goalRow(row+1,col) == -1) )
                    manhattanDist += 2;
                if ((data[row][col] == 0) || (data[row][col+1] == 0))
                    continue;
                if ( ( goalRow(row,col) == goalRow(row,col+1)) &&
                    ( goalCol(row,col)- goalCol(row,col+1) == -1) )
                    manhattanDist += 2;
            }
        }
        */        
        
    }
    
    private int goalRow(int row, int col) {
        int value = data[row][col];
        return (value-1) / dim;
    }
    
    private int goalCol(int row, int col) {
        int value = data[row][col];
        return (value-1) % dim;
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
        boolean res = true;
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                if (tmp.data[row][col] != this.data[row][col])
                    res = false;
            }
        }
        return res;         
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        
            ArrayList<Board> neighborsData = new ArrayList<Board>();
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