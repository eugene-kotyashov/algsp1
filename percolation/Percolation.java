/******************************************************************************
 *  Name:    Eugene 
 *  NetID:   euk
 *  Precept: P01
 * 
 *  Description:  Class for percolation simulation of a NxN
 *         grid using WeightedQuickUnion implementation for sites
 *         interconnection
 * @author eugene.kotyashov@gmail.com
 * 
******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

private static final byte FILLED = 1;
private static final byte OPENED = 0;

private int sizeN;
private int gridSize;
private int openSiteCount;

// simulation grid storage
// value of 1 means site is full and 0 - open
private byte[] grid;

private WeightedQuickUnionUF uf, ufFillCheck;

    
public Percolation(int N) // create N-by-N grid, with all sites blocked
{
    if (N < 1) 
        throw new java.lang.IllegalArgumentException();
    sizeN = N;
    openSiteCount = 0;
    gridSize = sizeN * sizeN;
    grid = new byte[gridSize];
// int uf instance with grid size plus 2 for virtual sites
    uf = new WeightedQuickUnionUF(gridSize + 2);
    ufFillCheck = new WeightedQuickUnionUF(gridSize + 1);
    for (int i = 0; i < sizeN; i++) {
        uf.union(0, i + 1);
        ufFillCheck.union(0, i + 1);
        uf.union(gridSize + 1, gridSize - i);
    }
    for (int i = 0; i < gridSize; i++) {
        grid[i] = FILLED;
    }
    
}

private boolean indexValid(int i, int j)
{
    return (((i > 0) || (i < sizeN + 1)) || ((j > 0) || (j < sizeN + 1)));
}

private int gridToArray(int i, int j)
{
    return (sizeN * (i - 1) + j - 1);
}

private int gridToUFArray(int i, int j)
{
    return (sizeN * (i - 1) + j - 1 + 1);
}

public void open(int i, int j) // open site (row i, column j) if it is not
    // already
{
    if (!indexValid(i, j)) {
        throw new java.lang.IllegalArgumentException();
    }
    if (!isOpen(i, j)) {
        grid[gridToArray(i, j)] = OPENED;
        openSiteCount++;
        // check if site is connected with its 4 neighbors
        //check connection with upper site
        if ((i < sizeN) && isOpen(i + 1, j)) {
            uf.union(gridToUFArray(i, j), gridToUFArray(i + 1, j));
            ufFillCheck.union(gridToUFArray(i, j), gridToUFArray(i + 1, j));
        }
        //check connection with lower cite
        if ((i > 1) && isOpen(i - 1, j)) {
            uf.union(gridToUFArray(i, j), gridToUFArray(i - 1, j));
            ufFillCheck.union(gridToUFArray(i, j), gridToUFArray(i - 1, j));
        }
        //check connection with left site
        if ((j > 1) && isOpen(i, j - 1)) {
            uf.union(gridToUFArray(i, j), gridToUFArray(i, j - 1));
            ufFillCheck.union(gridToUFArray(i, j), gridToUFArray(i, j - 1));
        }
        //check connection with right site
        if ((j < sizeN) && isOpen(i, j + 1)) {
            uf.union(gridToUFArray(i, j), gridToUFArray(i, j + 1));
            ufFillCheck.union(gridToUFArray(i, j), gridToUFArray(i, j + 1));
        }
    }
    
}

public boolean isOpen(int i, int j) // is site (row i, column j) open?
{
    if (!indexValid(i, j)) {
        throw new java.lang.IllegalArgumentException();
    }
    return (grid[gridToArray(i, j)] == OPENED);
    
}

public boolean isFull(int i, int j) // is site (row i, column j) full?
{    
    if (isOpen(i, j)) {
        return ufFillCheck.connected(0, gridToUFArray(i, j));
    } else {
        return false;
    }
}

public int numberOfOpenSites()       // number of open sites
{
    return openSiteCount;
}
public boolean percolates() // does the system percolate?
{
    if (openSiteCount > 0)
        return uf.connected(0, gridSize + 1);
    else
        return false;
}

}
