import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats 
{
	
    private double para = 1.96;
    private double avg, stdvalue;
    	
	private int nexps;
    public PercolationStats(int n, int trials)
    {
    	
    	if (n <= 0 || trials <= 0)
        {
    		throw new java.lang.IllegalArgumentException("input muse be positive!!!");
        }

        nexps = trials;
        double[] nthreshould = new double[trials];

    	for (int i = 0; i < trials; i++)
    	{
    		Percolation pc = new Percolation(n);      
            
            int nstep = 0;    
	        while (!pc.percolates())
	        {
	            int temp = StdRandom.uniform(n * n);           
	            int rowtemp = temp / n + 1;
	            int coltemp = temp % n + 1;
	            if (!pc.isOpen(rowtemp, coltemp))
	            {
	                pc.open(rowtemp, coltemp);
	                nstep++;
	            }
	        }
	        nthreshould[i] = (double) nstep / n / n;
    	}
        avg = StdStats.mean(nthreshould);
        stdvalue = StdStats.stddev(nthreshould);

    }    // perform trials independent experiments on an n-by-n grid
    public double mean()
    {
    	return avg;
    }                          // sample mean of percolation threshold
    public double stddev()
    {
    	return stdvalue;
    }                        // sample standard deviation of percolation threshold
    public double confidenceLo()
    {
    	return avg - para * stdvalue / Math.sqrt(nexps); 
    }                  // low  endpoint of 95% confidence interval
    public double confidenceHi()
    {
    	return avg +  para * stdvalue / Math.sqrt(nexps);
    }                  // high endpoint of 95% confidence interval

    public static void main(String[] args)
    {        
        int nside = StdIn.readInt();
        int ntrials = StdIn.readInt();     
    	
    	PercolationStats ps = new PercolationStats(nside, ntrials);
    	StdOut.println("mean value is               \t= " + ps.mean());
    	StdOut.println("standard deviation          \t= " + ps.stddev());
        StdOut.println("95% confidence interval is  \t= " + "[" + ps.confidenceLo() + "," + ps.confidenceHi() + "]");
    }        // test client (described below)
}