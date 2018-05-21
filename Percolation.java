import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int Nopen;
    private int side;
    private WeightedQuickUnionUF uf;  
    
    private boolean[][] Obstate;    
    
    public Percolation(int n)
    {
        side = n;
        Nopen = 0;
        int Nsite = side * side;
        Obstate = new boolean[side][side];
        uf = new WeightedQuickUnionUF(Nsite);
        for (int i = 0; i < side; i++){
            for (int j = 0; j < Obstate[i].length; j++){
                Obstate[i][j] = false;
            }
        }       
    }// create n-by-n grid, with all sites blocked
    public    void open(int row, int col) 
    {
        if (!isOpen(row, col))
            Obstate[row][col] = true;
        Nopen++;
        int temp = row * side + col;
        if ((row > 0) && (Obstate[row - 1][col] == true))
            uf.union(temp, temp - side);        
        if ((row < side - 1) && (Obstate[row + 1][col] == true))
            uf.union(temp, temp + side);
        if ((col < side - 1) && (Obstate[row][col + 1] == true))
            uf.union(temp, temp + 1);
        if ((col > 0) && (Obstate[row][col - 1] == true))
            uf.union(temp, temp - 1);  

    }// open site (row, col) if it is not open already
    public boolean isOpen(int row, int col)
    {
        return Obstate[row][col];
    }// is site (row, col) open?
    public boolean isFull(int row, int col) 
    {
        int siteid = row * side + col;
        for (int i = side * (side -1); i < side * side; i++)
            if (uf.connected(siteid, i)) return true;
        return false;
    }// is site (row, col) full?
    public     int numberOfOpenSites() 
    {
        return Nopen;
    }// number of open sites
    public boolean percolates()
    {
        boolean PerState = false;
        for (int i = 0; i < side; i++)
        {
            if (isFull(0, i))
            {
                PerState = true;
                break;
            }
        }
        return PerState;
    }// does the system percolate?
    
    public static void main(String[] args)
    {
        int N = StdIn.readInt();
        int nsites = N * N;
        int nstep = 0;
        
        Percolation pc = new Percolation(N);      
                
        while (!pc.percolates())
        {
            int temp = StdRandom.uniform(nsites);           
            int rowtemp = temp / N;
            int coltemp = temp % N;
            if (!pc.isOpen(rowtemp, coltemp))
            {
                pc.open(rowtemp, coltemp);
                nstep++;
            }
        }
        StdOut.println(nstep + " steps;" +" probality: " + (double) nstep/N/N );      
              
    }// test client (optional)
}
