import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;

public class KdTree {
    Node root;

    private static class Node {
        private Point2D p;           // the point
        private RectHV rect;         // the axis-aligned rectangle corresponding to this node
        private Node lb;             // the left/bottom subtree
        private Node rt;             // the right/top subtree
        private Node prev;           // previous node, null for the root node
        private boolean isVertical;  // this node splits the plane vertically or horizontally
        private int size;            // size of tree, includes the size of the subtrees

        Node(Node prev, Point2D p, boolean isVertical, int size)
        {
            this.prev = prev;
            this.p = p;
            rect = new RectHV(0, 0, 1, 1);
            this.isVertical = isVertical;
            this.size = size;
        }
    }

    // construct an empty kdtree of points
    public KdTree()
    {
    }

    // is the kdtree empty?
    public boolean isEmpty()
    {
        return size() == 0;
    }

    // number of points in the kdtree
    public int size()
    {
        return size(root);
    }

    private int size(Node x)
    {
        if (x == null)
        {
            return 0;
        }
        return x.size;
    }

    // add the point to the kdtree (if it is not already in the kdtree)
    public void insert(Point2D p)
    {
        if (!contains(p))
        {
            root = insert(root, null, p, true);
        }
    }

    private Node insert(Node x, Node prev, Point2D p, boolean isVertical)
    {
        if (x == null)
        {
            return new Node(prev, p, isVertical, 1);
        }

        int cmp = comparePoints(x, p, isVertical);
        if (cmp < 0)
        {
            x.lb = insert(x.lb, x, p, !isVertical);
        }
        else
        {
            x.rt = insert(x.rt, x, p, !isVertical);
        }
        x.size = 1 + size(x.lb) + size(x.rt);
        return x;
    }

    private Point2D get(Point2D p)
    {
        return get(root, p, true);
    }

    private Point2D get(Node x, Point2D p, boolean isVertical)
    {
        if (x == null)
        {
            return null;
        }

        int cmp = comparePoints(x, p, isVertical);
        if (p.equals(x.p))
        {
            return p;
        }
        else if (cmp < 0)
        {
            return get(x.lb, p, !isVertical);
        }
        else
        {
            return get(x.rt, p, !isVertical);
        }
    }

    private int comparePoints(Node x, Point2D p, boolean isVertical)
    {
        if (isVertical)
        {
            return Point2D.X_ORDER.compare(p, x.p);
        }
        return Point2D.Y_ORDER.compare(p, x.p);
    }

    // does the kdtree contain point p?
    public boolean contains(Point2D p)
    {
        return get(p) != null;
    }

    // draw all points to standard draw
    public void draw()
    {
        draw(root);
    }

    private void draw(Node x)
    {
        if (x == null)
        {
            return;
        }
        x.p.draw();
        draw(x.lb);
        draw(x.rt);
    }

    /*
    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect)
    {
    }

    // a nearest neighbor in the kdtree to point p;
    // null if the kdtree is empty
    public Point2D nearest(Point2D p)
    {
    }
    */

    // unit testing of the methods
    public static void main(String[] args)
    {
        KdTree kdtree = new KdTree();
        StdOut.println("Size: " + kdtree.size());
        StdOut.println("Is empty: " + kdtree.isEmpty());
        String filename = args[0];
        In in = new In(filename);
        while (!in.isEmpty())
        {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        StdOut.println("Size: " + kdtree.size());
        StdOut.println("Is empty: " + kdtree.isEmpty());
        
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
            kdtree.draw();

            // draw in red the nearest neighbor (using kdtree algorithm)
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            //kdtree.nearest(query).draw();
            StdDraw.setPenRadius(0.02);

            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}
