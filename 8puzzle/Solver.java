import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    private boolean solvable;
    private int solveMoves;
    private Queue<Board> solutionChain;

    public Solver(Board initial)
    {
        MinPQ<SearchNode> puzzleSolution = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinSolution = new MinPQ<SearchNode>();
        Board twin = initial.twin();
        SearchNode initialStart = new SearchNode(initial, 0, null);
        SearchNode twinStart = new SearchNode(twin, 0, null);
        puzzleSolution.insert(initialStart);
        twinSolution.insert(twinStart);
        while (!puzzleSolution.min().isGoal() && !twinSolution.min().isGoal())
        {
            SearchNode currentMin = puzzleSolution.delMin();
            Iterable<Board> currentNeighbors = currentMin.board.neighbors();
            for (Board board : currentNeighbors)
            {
                if (currentMin.prev != null && board.equals(currentMin.prev.board))
                {
                    continue;
                }
                SearchNode node = new SearchNode(board, currentMin.moves + 1, currentMin);
                puzzleSolution.insert(node);
            }

            SearchNode twinMin = twinSolution.delMin();
            Iterable<Board> twinNeighbors = twinMin.board.neighbors();
            for (Board board : twinNeighbors)
            {
                if (twinMin.prev != null &&  board.equals(twinMin.prev.board))
                {
                    continue;
                }
                SearchNode node = new SearchNode(board, twinMin.moves + 1, twinMin);
                twinSolution.insert(node);
            }
        }
        if (twinSolution.min().isGoal())
        {
            solvable = false;
            solveMoves = -1;
            solutionChain = null;
        }
        else
        {
            solvable = true;
            solveMoves = puzzleSolution.min().moves;
            SearchNode goal = puzzleSolution.min();
            Stack<Board> solutionsStack = new Stack<Board>();
            solutionChain = new Queue<Board>();
            while (goal != null)
            {
                solutionsStack.push(goal.board);
                goal = goal.prev;
            }
            while (!solutionsStack.isEmpty())
            {
                solutionChain.enqueue(solutionsStack.pop());
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable()
    {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()
    {
        return solveMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()
    {
        return solutionChain;
    }

    private class SearchNode implements Comparable<SearchNode>
    {
        SearchNode prev;
        int moves;
        int priority;
        Board board;

        public SearchNode(Board board, int moves, SearchNode prev)
        {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            priority = this.board.manhattan();
        }

        public boolean isGoal()
        {
            return board.isGoal();
        }

        public int compareTo(SearchNode that)
        {
            final int priority_this = priority + moves;
            final int priority_that = that.priority + that.moves;
            if (priority_this < priority_that)
            {
                return -1;
            }
            if (priority_this > priority_that)
            {
                return 1;
            }
            if (priority < that.priority)
            {
                return -1;
            }
            if (priority > that.priority)
            {
                return 1;
            }
            return 0;
        }
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
                StdOut.println(board);
            }
        }
    }
}
