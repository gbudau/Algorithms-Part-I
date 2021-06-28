import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf;
    private int openSites;
    private final int gridSize;
    private boolean[] grid;
    private final int lastRow;
    private final int lastCol;
    private final int virtualTop;
    private final int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException("Invalid grid size");
        }
        // add two additional sites
        // one its virtual top site and one virtual bottom
        uf = new WeightedQuickUnionUF(n * n + 2);
        openSites = 0;
        // the grid is a rectangle, this represents its height and width
        gridSize = n;
        grid = new boolean[n * n + 2];
        lastRow = gridSize - 1;
        lastCol = gridSize - 1;
        virtualTop = gridSize * lastRow + lastCol + 1;
        virtualBottom = gridSize * lastRow + lastCol + 2;
    }

    // check if the row and column are valid
    private void checkValidIndex(int row, int col)
    {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
        {
            throw new IllegalArgumentException("Invalid grid size");
        }
    }

    private void connectUpperNeighbour(int currentPosition, int row, int col)
    {
        if (row > 0)
        {
            int upperNeighbour = gridSize * (row - 1) + col;
            if (isOpen(row, col + 1))
            {
                uf.union(currentPosition, upperNeighbour);
            }
        }
    }

    private void connectLowerNeighbour(int currentPosition, int row, int col)
    {
        if (row < lastRow)
        {
            int lowerNeighbour = gridSize * (row + 1) + col;
            if (isOpen(row + 2, col + 1))
            {
                uf.union(currentPosition, lowerNeighbour);
            }
        }
    }

    private void connectLeftNeighbour(int currentPosition, int row, int col)
    {
        if (col > 0)
        {
            int leftNeighbour = gridSize * row + col - 1;
            if (isOpen(row + 1, col))
            {
                uf.union(currentPosition, leftNeighbour);
            }
        }
    }

    private void connectRightNeighbour(int currentPosition, int row, int col)
    {
        if (col < lastCol)
        {
            int rightNeighbour = gridSize * row + col + 1;
            if (isOpen(row + 1, col + 2))
            {
                uf.union(currentPosition, rightNeighbour);
            }
        }
    }

    private void connectVirtualTop(int currentPosition, int row)
    {
        if (row == 0)
        {
            uf.union(currentPosition, virtualTop);
        }
    }

    private void connectVirtualBottom(int currentPosition, int row)
    {
        if (row == lastRow)
        {
            uf.union(currentPosition, virtualBottom);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        checkValidIndex(row, col);
        if (isOpen(row, col))
        {
            return;
        }
        // The row and col indexes start from 1 so we need to substract one
        // to access the real index in the grid
        --row;
        --col;
        ++openSites;
        grid[gridSize * row + col] = true;
        int currentPosition = gridSize * row + col;
        connectUpperNeighbour(currentPosition, row, col);
        connectLowerNeighbour(currentPosition, row, col);
        connectLeftNeighbour(currentPosition, row, col);
        connectRightNeighbour(currentPosition, row, col);
        connectVirtualTop(currentPosition, row);
        connectVirtualBottom(currentPosition, row);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        checkValidIndex(row, col);
        // The row and col indexes start from 1 so we need to substract one
        // to access the real index in the grid
        --row;
        --col;
        return grid[gridSize * row + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        return isOpen(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return uf.find(virtualTop) == uf.find(virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args)
    {

        System.out.println("Creating percolation with 3 x 3 grid");
        Percolation percolation = new Percolation(3);
        System.out.print("Number of open sites: ");
        System.out.println(percolation.numberOfOpenSites());
        System.out.print("percolates?: ");
        System.out.println(percolation.percolates());
        System.out.print("isOpen 1,1?: ");
        System.out.println(percolation.isOpen(1, 1));
        System.out.print("isFull 1,1?: ");
        System.out.println(percolation.isFull(1, 1));
        System.out.println("opening 1, 1");
        percolation.open(1, 1);
        System.out.print("isOpen 1,1?: ");
        System.out.println(percolation.isOpen(1, 1));
        System.out.print("isFull 1,1?: ");
        System.out.println(percolation.isFull(1, 1));
        System.out.print("percolates?: ");
        System.out.println(percolation.percolates());
        System.out.println("opening 2, 1");
        percolation.open(2, 1);
        System.out.print("percolates?: ");
        System.out.println(percolation.percolates());
        System.out.print("number of open sites: ");
        System.out.println("opening 3, 1");
        percolation.open(3, 1);
        System.out.print("percolates?: ");
        System.out.println(percolation.percolates());
        System.out.print("number of open sites: ");
        System.out.println(percolation.numberOfOpenSites());
    }
}
