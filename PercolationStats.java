import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

	private double[] nthreshould;
	private int size;
	private int nexps;
    public PercolationStats(int n, int trials)
    {
    	size = n;
    	nexps = trials;
    	nthreshould = new double[nexps];
    	if (n <= 0 || trials <= 0)
    		throw new java.lang.IllegalArgumentException();

    	for (int i = 0; i < nexps; i++)
    	{
    		Percolation pc = new Percolation(n);      
            
            int nstep = 0;    
	        while (!pc.percolates())
	        {
	            int temp = StdRandom.uniform(size * size);           
	            int rowtemp = temp / n;
	            int coltemp = temp % n;
	            if (!pc.isOpen(rowtemp, coltemp))
	            {
	                pc.open(rowtemp, coltemp);
	                nstep++;
	            }
	        }
	        nthreshould[i] = (double) nstep / n / n;
    	}

    }    // perform trials independent experiments on an n-by-n grid
    public double mean()
    {
    	return StdStats.mean(nthreshould);
    }                          // sample mean of percolation threshold
    public double stddev()
    {
    	return StdStats.stddev(nthreshould);
    }                        // sample standard deviation of percolation threshold
    public double confidenceLo()
    {
    	return StdStats.mean(nthreshould) - 1.96 * StdStats.stddev(nthreshould) / nexps; 
    }                  // low  endpoint of 95% confidence interval
    public double confidenceHi()
    {
    	return StdStats.mean(nthreshould) + 1.96 * StdStats.stddev(nthreshould) / nexps;
    }                  // high endpoint of 95% confidence interval

    public static void main(String[] args)
    {
    	int nside = StdIn.readInt();
    	int ntrials = StdIn.readInt();

    	PercolationStats ps = new PercolationStats(nside, ntrials);
    	StdOut.println("mean value is: \t" + ps.mean());
    	StdOut.println("standard deviation: \t" + ps.stddev());
    }        // test client (described below)
}