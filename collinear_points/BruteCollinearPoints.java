import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int nSegments;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] pointsArray)
    {
        if (pointsArray == null || isAnyPointNull(pointsArray))
        {
            throw new IllegalArgumentException(
                    "Error: BruteCollinearPoints: Invalid argument: null");
        }
        Point[] points = createPointsCopy(pointsArray);
        Arrays.sort(points);
        if (isAnyPointDuplicate(points))
        {
            throw new IllegalArgumentException(
                "Error: FastCollinearPoints: Invalid argument: equal points");
        }
        nSegments = 0;
        lineSegments = null;
        Arrays.sort(points);
        for (int i = 0; i < points.length; ++i) {
            for (int j = i + 1; j < points.length; ++j)
            {
                for (int k = j + 1; k < points.length; ++k)
                {
                    for (int h = k + 1; h < points.length; ++h)
                    {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k]; 
                        Point s = points[h];
                        Point minPoint = findMinPoint(p, q, r, s);
                        if (p.slopeTo(q) == p.slopeTo(r) &&
                                p.slopeTo(r) == p.slopeTo(s))
                        {
                            LineSegment ls = new LineSegment(p, s);
                            addLineSegment(ls);
                        }
                    }
                }
            }
        }
    }

    private static Point[] createPointsCopy(Point[] points)
    {
        Point[] copy = new Point[points.length];
        for (int i = 0; i < points.length; ++i)
        {
            copy[i] = points[i];
        }
        return copy;
    }

    private static boolean isAnyPointNull(Point[] points)
    {
        for (int i = 0; i < points.length; ++i)
        {
            if (points[i] == null)
                return true;
        }
        return false;
    }

    private static boolean isAnyPointDuplicate(Point[] points)
    {
        if (points.length < 2)
        {
            return false;
        }
        for (int i = 1; i < points.length; ++i)
        {
            if (points[i - 1].compareTo(points[i]) == 0)
            {
                return true;
            }
        }
        return false;
    }

    private void addLineSegment(LineSegment ls)
    {
        if (lineSegments == null)
        {
            lineSegments = new LineSegment[1];
            lineSegments[0] = ls;
        }
        else if (nSegments < lineSegments.length)
        {
            lineSegments[nSegments] = ls;
        }
        else
        {
            LineSegment[] copy = new LineSegment[lineSegments.length * 2];
            for (int i = 0; i < lineSegments.length; ++i)
            {
                copy[i] = lineSegments[i];
            }
            lineSegments = copy;
            lineSegments[nSegments] = ls;
        }
        ++nSegments;
    }

    // the number of line segments
    public int numberOfSegments()
    {
        return nSegments;
    }

    // the line segments
    public LineSegment[] segments()
    {
        LineSegment[] segmentsFound = new LineSegment[nSegments];
        for (int i = 0; i < nSegments; ++i)
        {
            segmentsFound[i] = lineSegments[i];
        }
        return segmentsFound;
    }

    /**
     * Unit tests the BruteCollinearPoints data type.
     */
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
        for (Point p : points) {
            p.draw();
        }
        LineSegment[] lineSegments = collinearPoints.segments();
        for (LineSegment line : lineSegments) {
            line.draw();
        }
        StdDraw.show();
        System.out.println("Number of segments: " + collinearPoints.numberOfSegments());
    }
}
