import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
	private int nSegments;
	private LineSegment[] lineSegments;

	// finds all line segments containing 4 or more points
	public FastCollinearPoints(Point[] points)
	{
		if (points == null || isAnyPointNull(points))
		{
			throw new IllegalArgumentException(
					"Error: FastCollinearPoints: Invalid argument: null");
		}
		Arrays.sort(points);
		if (isAnyPointDuplicate(points))
		{
			throw new IllegalArgumentException(
				"Error: FastCollinearPoints: Invalid argument: equal points");
		}
		for (int i = 0; i < points.length; ++i)
		{
			Point[] copy = create_copy(points);
			Arrays.sort(copy, points[i].slopeOrder());
			int pointIndex = searchPoint(copy, points[i]);
			if (pointIndex == -1)
			{
				continue;
			}
			int minIndex = pointIndex;
			int maxIndex = findMaxIndex(copy, pointIndex, points[i]);
			--minIndex;
			Point temp = copy[minIndex];
			copy[minIndex] = copy[0];
			copy[0] = temp;
			// TODO
			// Check if that LineSegment has not been added already
			// Add the LineSegment
			Arrays.sort(copy, minIndex, maxIndex + 1);
			Point minPoint = copy[minIndex];
			Point maxPoint = copy[maxIndex];
			for (int j = 0; j < copy.length; ++j)
			{
				System.out.print(points[i].slopeTo(copy[j]) + " ");
			}
			System.out.println();
			System.out.println("minIndex: " + minIndex + ", maxIndex: " + maxIndex);
			System.out.println();
			if ((maxIndex - minIndex) > 2)
			{
				LineSegment ls = new LineSegment(minPoint, maxPoint);
				addLineSegment(ls);
			}
		}
	}

	private static int searchPoint(Point[] points, Point p)
	{
		for (int i = 0; i < points.length - 1; ++i)
		{
			if (p.slopeTo(points[i]) == p.slopeTo(points[i + 1]))
			{
				return i;
			}
		}
		return -1;
	}

	private static int findMaxIndex(Point[] points, int pointIndex, Point p)
	{
		int maxIndex = pointIndex;
		for (; maxIndex < points.length; ++maxIndex)
		{
			if (p.slopeTo(points[maxIndex]) != p.slopeTo(points[pointIndex]))
			{
				break;
			}
		}
		return maxIndex - 1;
	}

	private static Point[] create_copy(Point[] points)
	{
		Point[] copy = new Point[points.length];
		for (int i = 0; i < points.length; ++i)
		{
			copy[i] = points[i];
		}
		return copy;
	}

	// find if there is any duplicate point in a sorted array of points
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

	private static boolean isAnyPointNull(Point[] points)
	{
		for (int i = 0; i < points.length; ++i)
		{
			if (points[i] == null)
				return true;
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
	 * Unit tests the FastCollinearPoints data type.
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
		FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
		for (Point p : points) {
			p.draw();
		}
		LineSegment[] lineSegments = collinearPoints.segments();
		for (LineSegment l : lineSegments) {
			l.draw();
		}
		StdDraw.show();
	}
}
