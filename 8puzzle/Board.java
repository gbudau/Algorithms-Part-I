import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int N;          // number of rows/columns
    private final int[][] tiles;  // N-by-N array

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        if (tiles == null)
        {
            throw new IllegalArgumentException(
                    "Error: Board: Invalid argument: null");
        }
        N = tiles.length;
        this.tiles = new int[N][N];
        for (int row = 0; row < N; ++row)
        {
            if (tiles.length != N)
            {
                throw new IllegalArgumentException(
                        "Error: Board: Invalid array size");
            }
            for (int col = 0; col < N; ++col)
            {
                this.tiles[row][col] = tiles[row][col];
            }
        }
    }

    // string representation of this board
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int row = 0; row < N; ++row)
        {
            for (int col = 0; col < N; ++col)
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
        return N;
    }

    // number of tiles out of place
    public int hamming()
    {
        int sum = 0;
        int tile = 1;
        int lastRow = N - 1;
        int lastCol = N - 1;
        int emptyTile = 0;
        for (int row = 0; row < N; ++row) {
            for (int col = 0; col < N; ++col)
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
        // TODO
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        if (N != 0)
        {
            return true;
        }
        int lastRow = N - 1;
        int lastCol = N - 1;
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
        return N == that.N && Arrays.deepEquals(tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        // Stack nb;
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
        // TODO
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
        System.out.println(initial.toString());
        System.out.println("Is goal board?: " + initial.isGoal());
        System.out.println("Hamming distance: " + initial.hamming());
    }
}
