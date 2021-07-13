import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int n;          // number of rows/columns
    private final int[][] tiles;  // n-by-n array

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        if (tiles == null)
        {
            throw new IllegalArgumentException(
                    "Error: Board: Invalid argument: null");
        }
        n = tiles.length;
        if (n < 2)
        {
            throw new IllegalArgumentException(
                    "Error: Board: Invalid board size");
        }
        this.tiles = copyTiles(tiles);
    }

    private int[][] copyTiles(int[][] tilesArray)
    {
        int length = tilesArray.length;
        int[][] copy = new int[length][length];
        for (int row = 0; row < length; ++row)
        {
            if (tilesArray[row].length != length)
            {
                throw new IllegalArgumentException("Error: Invalid array size");
            }
            for (int col = 0; col < length; ++col)
            {
                copy[row][col] = tilesArray[row][col];
            }
        }
        return copy;
    }

    // string representation of this board
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int row = 0; row < n; ++row)
        {
            for (int col = 0; col < n; ++col)
            {
                s.append(String.format("%2d ", tiles[row][col]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension()
    {
        return n;
    }

    // number of tiles out of place
    public int hamming()
    {
        int sum = 0;
        int tile = 1;
        final int lastRow = n - 1;
        final int lastCol = n - 1;
        for (int row = 0; row < n; ++row) {
            for (int col = 0; col < n; ++col)
            {
                if (tiles[row][col] != tile)
                {
                    if (row == lastRow && col == lastCol)
                    {
                        continue;
                    }
                    else
                    {
                        ++sum;
                    }
                }
                ++tile;
            }
        }
        return sum;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
        int sum = 0;
        int tile = 1;
        final int emptyTile = 0;
        for (int row = 0; row < n; ++row) {
            for (int col = 0; col < n; ++col)
            {
                final int currentTile = tiles[row][col];
                final int goalRow = (currentTile - 1) / n;
                final int goalCol = (currentTile - 1) % n;
                if (currentTile != tile &&
                        currentTile != emptyTile)
                {
                    sum += Math.abs(col - goalCol);
                    sum += Math.abs(row - goalRow);
                }
                ++tile;
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        int lastRow = n - 1;
        int lastCol = n - 1;
        int emptyTile = 0;
        return hamming() == 0 && tiles[lastRow][lastCol] == emptyTile;
    }

    // does this board equal y?
    public boolean equals(Object aThat)
    {
        if (this == aThat)
        {
            return true;
        }
        if (aThat == null)
        {
            return false;
        }
        if (this.getClass() != aThat.getClass())
        {
            return false;
        }
        Board that = (Board) aThat;
        return n == that.n && Arrays.deepEquals(tiles, that.tiles);
    }

    private int[][] cloneBoard() {
        int[][] clone = new int[n][n];
        for (int row = 0; row < n; ++row)
        {
            for (int col = 0; col < n; ++col)
            {
                clone[row][col] = tiles[row][col];
            }
        }
        return clone;

    }

    private Board slideTopNeighbor(int emptyTileRow, int emptyTileCol)
    {
        if (emptyTileRow == 0)
        {
            return null;
        }
        int[][] topNeighbor = cloneBoard();
        topNeighbor[emptyTileRow][emptyTileCol] = topNeighbor[emptyTileRow - 1][emptyTileCol];
        topNeighbor[emptyTileRow - 1][emptyTileCol] = 0;
        return new Board(topNeighbor);
    }

    private Board slideRightNeighbor(int emptyTileRow, int emptyTileCol)
    {
        if (emptyTileCol == n - 1)
        {
            return null;
        }
        int[][] rightNeighbor = cloneBoard();
        rightNeighbor[emptyTileRow][emptyTileCol] = rightNeighbor[emptyTileRow][emptyTileCol + 1];
        rightNeighbor[emptyTileRow][emptyTileCol + 1] = 0;
        return new Board(rightNeighbor);
    }

    private Board slideBottomNeighbor(int emptyTileRow, int emptyTileCol)
    {
        if (emptyTileRow == n - 1)
        {
            return null;
        }
        int[][] bottomNeighbor = cloneBoard();
        bottomNeighbor[emptyTileRow][emptyTileCol] = bottomNeighbor[emptyTileRow + 1][emptyTileCol];
        bottomNeighbor[emptyTileRow + 1][emptyTileCol] = 0;
        return new Board(bottomNeighbor);
    }

    private Board slideLeftNeighbor(int emptyTileRow, int emptyTileCol)
    {
        if (emptyTileCol == 0)
        {
            return null;
        }
        int[][] leftNeighbor = cloneBoard();
        leftNeighbor[emptyTileRow][emptyTileCol] = leftNeighbor[emptyTileRow][emptyTileCol - 1];
        leftNeighbor[emptyTileRow][emptyTileCol - 1] = 0;
        return new Board(leftNeighbor);
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        Stack<Board> nb = new Stack<Board>();
        int emptyTileRow = -1;
        int emptyTileCol = -1;
        int emptyTile = 0;
        for (int row = 0; row < n; ++row)
        {
            for (int col = 0; col < n; ++col)
            {
                if (tiles[row][col] == emptyTile)
                {
                    emptyTileRow = row;
                    emptyTileCol = col;
                }
            }
        }
        Board topNeighbor = slideTopNeighbor(emptyTileRow, emptyTileCol);
        if (topNeighbor != null)
        {
            nb.push(topNeighbor);
        }
        Board rightNeighbor = slideRightNeighbor(emptyTileRow, emptyTileCol);
        if (rightNeighbor != null)
        {
            nb.push(rightNeighbor);
        }
        Board bottomNeighbor = slideBottomNeighbor(emptyTileRow, emptyTileCol);
        if (bottomNeighbor != null)
        {
            nb.push(bottomNeighbor);
        }
        Board leftNeighbor = slideLeftNeighbor(emptyTileRow, emptyTileCol);
        if (leftNeighbor != null)
        {
            nb.push(leftNeighbor);
        }
        return nb;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
        int[][] twinTiles = new int[n][n];
        for (int row = 0; row < n; ++row)
        {
            for (int col = 0; col < n; ++col)
            {
                twinTiles[row][col] = tiles[row][col];
            }
        }
        int firstPairRow = -1;
        int firstPairCol = -1;
        int secondPairRow = -1;
        int secondPairCol = -1;
        for (int row = 0; row < n; ++row)
        {
            for (int col = 0; col < n; ++col)
            {
                if (twinTiles[row][col] != 0
                        && firstPairRow == -1
                        && firstPairCol == -1)
                {
                    firstPairRow = row;
                    firstPairCol = col;
                }
                else if (twinTiles[row][col] != 0
                        && secondPairRow == -1
                        && secondPairCol == -1)
                {
                    secondPairRow = row;
                    secondPairCol = col;
                    int temp = twinTiles[firstPairRow][firstPairCol];
                    twinTiles[firstPairRow][firstPairCol] =
                        twinTiles[secondPairRow][secondPairCol];
                    twinTiles[secondPairRow][secondPairCol] = temp;
                    return new Board(twinTiles);
                }
            }
        }
        return null;
    }

    // unit testing
    public static void main(String[] args)
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        System.out.println("Current board:");
        System.out.println(initial.toString());
        System.out.println("Is goal board?: " + initial.isGoal());
        System.out.println("Hamming distance: " + initial.hamming());
        System.out.println("Manhattan distance: " + initial.manhattan());
        System.out.println("Twin tile:");
        System.out.println(initial.twin().toString());
        System.out.println("Board neighbors:");
        Iterable<Board> neighbors = initial.neighbors();
        for (Board nb : neighbors) {
            System.out.println(nb.toString());
        }
    }
}
