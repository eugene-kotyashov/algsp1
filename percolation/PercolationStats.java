/******************************************************************************
 *  Name:    Eugene 
 *  NetID:   euk
 *  Precept: P01
 * 
 *  Description: Class for running the percolation problem
 *    performing T simulations on NxN grid
 * Execution : java PercolationStats <grid size N> <number of runs T>
 * @author eugene.kotyashov@gmail.com
 *****************************************************************************/
public class PercolationStats {

    private double mean;
    private double stddev;
    private double confLo;
    private double confHi;
// perform T independent computational
// experiments on an N-by-N grid
    public PercolationStats(int N, int T) 
    {
        if ((N <= 0) || (T <= 0))
            throw new java.lang.IllegalArgumentException();
        int gridSize = N * N;
        double[] samples = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation pcl = new Percolation(N);
            int openCount = 0;
            while ((!pcl.percolates()) && (openCount < gridSize)) {
                int row = StdRandom.uniform(1, N + 1);
                int col = StdRandom.uniform(1, N + 1);
                if (!pcl.isOpen(row, col)) {
                    pcl.open(row, col);
                    openCount++;
                }
            }
            samples[i] = (double) openCount / (double) gridSize;
            
        }
        mean = StdStats.mean(samples);
        stddev = StdStats.stddev(samples);
        confLo = mean - 1.96 * stddev / (Math.sqrt((double) T));
        confHi = mean + 1.96 * stddev / (Math.sqrt((double) T));
    }
    
    public double mean() // sample mean of percolation threshold
    {
        return mean;
        
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() 
    {
        return stddev;
    }
    // returns lower bound of the 95% confidence
    // interval
    public double confidenceLo() 
    {
        return confLo;
    }
    // returns upper bound of the 95% confidence
    // interval
    public double confidenceHi()
    {
        return confHi;
        
    }
    
    public static void main(String[] args) // test client
    {
        if (args.length == 2) {
            try {
                PercolationStats ps = 
                    new PercolationStats(
                                         new Integer(args[0]),
                                         new Integer(args[1]));
                StdOut.println("mean =" + ps.mean());
                StdOut.println("stddev =" + ps.stddev());
                StdOut.println("95% confidence interval = " + ps.confidenceLo()
                                   + ", " + ps.confidenceHi());
            } catch (java.lang.IllegalArgumentException e) {
                StdOut.println("grid size and number"
                                   +"of experiments must be > 0!");
            }
            
        } else {
            StdOut.println("Enter  grid size and number of experiments");
        }
    }
}
