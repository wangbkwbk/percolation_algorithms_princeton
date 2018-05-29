// Row 1 is the top row

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int nopen;
    private final int side, nsite;
    private final WeightedQuickUnionUF uf, uftop; // uftop excludes the connection between bottom row and virtual bottom site;  
    
    private boolean[] obstate;    
    
    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new java.lang.IllegalArgumentException("n muse be between 1 and side length!!!");
        }

        side = n;
        nopen = 0;
        nsite = side * side + 2;
        obstate = new boolean[nsite];
        uf = new WeightedQuickUnionUF(nsite);
        uftop = new WeightedQuickUnionUF(nsite - 1);

        obstate[0] = true;
        for (int i = 1; i < nsite - 1; i++)
        {
            obstate[i] = false;
        }
        obstate[nsite - 1] = true;       
    } // create n-by-n grid, with all sites blocked
    
    private int convert2dto1d(int row, int col) // convert 2d index to 1d index
    {
        return (row - 1) * side + col;
    }

    public    void open(int row, int col) 
    {
        if (row < 1 || col < 1 || row > side || col > side)
        {
            throw new java.lang.IllegalArgumentException("n muse be between 1 and side length!!!");
        }

        int temp = convert2dto1d(row, col); // 2d to 1d converter

        if (!isOpen(row, col))
        {
            obstate[temp] = true;
            nopen++;
        }
        
        if (row > 1 && obstate[temp -side])
        {
            uf.union(temp, temp - side);
            uftop.union(temp, temp - side);        
        }
        if (row < side && obstate[temp + side])
        {
            uf.union(temp, temp + side);
            uftop.union(temp, temp + side);
        }
        if (col < side && obstate[temp + 1])
        {
            uf.union(temp, temp + 1);
            uftop.union(temp, temp + 1);
        }
        if (col > 1 && obstate[temp - 1])
        {
            uf.union(temp, temp - 1);
            uftop.union(temp, temp - 1);  
        }
        if (row == 1)
        {
            uf.union(temp, 0);
            uftop.union(temp, 0);
        }
        if (row == side)
        {
            uf.union(temp, nsite - 1);
        }

    } // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col)
    {
        if (row < 1 || col < 1 || row > side || col > side) 
        {
            throw new java.lang.IllegalArgumentException("row and column muse be between 1 and side length!!!");
        }
        int temp = convert2dto1d(row, col);
        return obstate[temp];
    } // is site (row, col) open?

    public boolean isFull(int row, int col) 
    {
        if (row < 1 || col < 1 || row > side || col > side)
        {
            throw new java.lang.IllegalArgumentException("row and column muse be between 1 and side length!!!");
        }
        int temp = convert2dto1d(row, col);
        if (uftop.connected(temp, 0) && isOpen(row, col)) return true;
        return false;        
    } // is site (row, col) full?

    public int numberOfOpenSites() 
    {
        return nopen;
    } // number of open sites

    public boolean percolates()
    {
        boolean perState = uf.connected(0, nsite - 1) ? true : false;
        return perState;        
    } // does the system percolate?
    
    public static void main(String[] args)
    {
        int n = StdIn.readInt();
        int ngrid = n * n;
        int nstep = 0;
        
        Percolation pc = new Percolation(n);      
                
        while (!pc.percolates())
        {
            int temp = StdRandom.uniform(ngrid);           
            int rowtemp = temp / n + 1;
            int coltemp = temp % n + 1;
            if (!pc.isOpen(rowtemp, coltemp))
            {
                pc.open(rowtemp, coltemp);
                nstep++;
            }
        }
        StdOut.println(nstep + " steps;" +" probality: " + (double) nstep/n/n);      
              
    } // test client (optional)
}
