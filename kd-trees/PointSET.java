import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class PointSET {
    SET<Point2D> set;

    // construct an empty set of points
    public PointSET()
    {
        set = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty()
    {
        return set.isEmpty();
    }

    // number of points in the set
    public int size()
    {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException(
                    "insert: invalid argument: null");
        }
        if (!set.contains(p))
        {
            set.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException(
                    "contains: invalid argument: null");
        }
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw()
    {
        for (Point2D p : set)
        {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null)
        {
            throw new IllegalArgumentException(
                    "range: invalid argument: null");
        }

        Queue<Point2D> inside = new Queue<Point2D>();
        for (Point2D p : set)
        {
            if (rect.contains(p))
            {
                inside.enqueue(p);
            }
        }
        return inside;
    }

    // a nearest neighbor in the set to point p;
    // null if the set is empty
    public Point2D nearest(Point2D p)
    {
        if (p == null)
        {
            throw new IllegalArgumentException(
                    "nearest: invalid argument: null");
        }
        Point2D neighbor = null;
        for (Point2D point : set)
        {
            if (neighbor == null ||
                    point.distanceSquaredTo(p) < neighbor.distanceSquaredTo(p))
            {
                neighbor = point;
            }
        }
        return neighbor;
    }

    // unit testing of the methods
    public static void main(String[] args)
    {
        PointSET pointset = new PointSET();
        StdOut.println("Size: " + pointset.size());
        StdOut.println("Is empty: " + pointset.isEmpty());
        String filename = args[0];
        In in = new In(filename);
        while (!in.isEmpty())
        {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            pointset.insert(p);
        }
        
        // process nearest neighbor queries
        StdDraw.enableDoubleBuffering();
        while (true)
        {
            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            pointset.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            pointset.nearest(query).draw();
            StdDraw.setPenRadius(0.02);

            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}
