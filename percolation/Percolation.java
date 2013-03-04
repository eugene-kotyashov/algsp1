/**
 * @author eugene.kotyashov@gmail.com Class for percolation simulation of a NxN
 *         grid using WeightedQuickUnion implementation for sites
 *         interconnection last modified 28.12.2013
 */

public class Percolation {

private static final byte FILLED = 1;
private static final byte OPENED = 0;

private int sizeN;

// simulation grid storage
// value of 1 means site is full and 0 - open
private byte[] grid;

private WeightedQuickUnionUF uf;

public Percolation(int N) // create N-by-N grid, with all sites blocked
{
    sizeN = N;
    int gridSize = sizeN * sizeN;
    grid = new byte[gridSize];
// int uf instance with grid size plus 2 for virtual sites
    uf = new WeightedQuickUnionUF(gridSize + 2);
    for (int i = 0; i < sizeN; i++) {
        uf.union(0, i + 1);
        uf.union(gridSize + 1, gridSize - i);
    }
    for (int i = 0; i < gridSize; i++) {
        grid[i] = FILLED;
    }
    
}

public void open(int i, int j) // open site (row i, column j) if it is not
    // already
{
    if (((i < 1) || (i > sizeN)) || ((j < 1) || (j > sizeN))) {
        throw new java.lang.IndexOutOfBoundsException();
    }
    if (!isOpen(i, j)) {
        grid[(i - 1) * sizeN + (j - 1)] = OPENED;
        // check if site is connected with its 4 neighbors
        int ufCurId = sizeN * (i - 1) + j - 1 + 1; // corresponds to row i
        // col j
        int ufTestId = -1;
        if ((i < sizeN) && isOpen(i + 1, j)) {
            ufTestId = sizeN * (i + 1 - 1) + j - 1 + 1;
            uf.union(ufCurId, ufTestId);
        }
        if ((i > 1) && isOpen(i - 1, j)) {
            ufTestId = sizeN * (i - 1 - 1) + j - 1 + 1;
            uf.union(ufCurId, ufTestId);
        }
        if ((j > 1) && isOpen(i, j - 1)) {
            ufTestId = sizeN * (i - 1) + j - 1 - 1 + 1;
            uf.union(ufCurId, ufTestId);
        }
        if ((j < sizeN) && isOpen(i, j + 1)) {
            ufTestId = sizeN * (i - 1) + j + 1 - 1 + 1;
            uf.union(ufCurId, ufTestId);
        }
    }
    
}

public boolean isOpen(int i, int j) // is site (row i, column j) open?
{
    if (((i < 1) || (i > sizeN)) || ((j < 1) || (j > sizeN))) {
        throw new java.lang.IndexOutOfBoundsException();
    }
    return (grid[(i - 1) * sizeN + (j - 1)] == OPENED);
    
}

public boolean isFull(int i, int j) // is site (row i, column j) full?
{    
    if (isOpen(i, j)) {
        return uf.connected(0, (i - 1) * sizeN + (j - 1) + 1);
    } else {
        return false;
    }
}

public boolean percolates() // does the system percolate?
{
    return uf.connected(0, sizeN * sizeN + 1);
}

}
