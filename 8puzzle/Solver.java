import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
    }

    // is the initial board solvable?
    public boolean isSolvable()
    {
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()
    {
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()
    {
    }

    // test client
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int row = 0; row < n; ++row)
        {
            for (int col = 0; col < n; ++col)
            {
                tiles[row][col] = in.readInt();
            }
        }

        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);
        if (!solver.isSolvable())
        {
            StdOut.println("No solution possible");
        }
        else
        {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
            {
                Stdout.println(board);
            }
        }
    }
}
