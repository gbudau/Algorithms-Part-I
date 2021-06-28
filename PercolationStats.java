import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.960;
    private final double[] percolationThreshold;
    private final int trials;
    private final double zScore;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        this.trials = trials;
        final int gridSize = n * n;
        percolationThreshold = new double[trials];
        for (int i = 0; i < trials; ++i)
        {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates())
            {
                int randomIndex = StdRandom.uniform(gridSize);
                int row = randomIndex / n + 1;
                int col = randomIndex % n + 1;
                if (!percolation.isOpen(row, col))
                {
                    percolation.open(row, col);
                }
            }
            percolationThreshold[i] = (double) percolation.numberOfOpenSites() / gridSize;
        }
        zScore = CONFIDENCE_95 * (stddev() / Math.sqrt(trials));
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(percolationThreshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(percolationThreshold);
    }

    // low endpoint of 95% confidence interval
    // https://www.mathsisfun.com/data/confidence-interval.html
    public double confidenceLo()
    {
        return mean() - zScore;
    }

    // high endpoint of 95% confidence interval
    // https://www.mathsisfun.com/data/confidence-interval.html
    public double confidenceHi()
    {
        return mean() + zScore;
    }

    // test client
    public static void main(String[] args)
    {
        if (args.length != 2) {
            System.out.println("Usage: java PercolationStats grid_size number_of_trials");
            return;
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        if (n <= 0 || trials <= 0)
        {
            throw new IllegalArgumentException("Invalid arguments");
        }
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        String confidence = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
        System.out.println("95% confidence interval = " + confidence);
    }
}
