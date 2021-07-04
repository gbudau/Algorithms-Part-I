import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

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
			int pointIndex = binarySearch(copy, points[i]);

			System.out.println("Point at current index: " + points[i].toString());
			System.out.println("Point found by binary search " + points[pointIndex].toString());
			System.out.println("i index: " + i + ", point index: " + pointIndex);
			System.out.println();

			int minIndex = findMinIndex(copy, pointIndex);
			int maxIndex = findMaxIndex(copy, pointIndex);
			if (minIndex != -1 && maxIndex != -1 &&
					(maxIndex - minIndex) > 3 &&
					points[i].compareTo(points[minIndex]) == 0)
			{
				LineSegment ls = new LineSegment(
						points[minIndex], points[maxIndex]);
				addLineSegment(ls);
			}
		}
	}

	private static int binarySearch(Point[] points, Point p)
	{
		int lo = 0;
		int hi = points.length - 1;
		while (lo <= hi)
		{
			int mid = lo + (hi - lo) / 2;
			if (p.compareTo(points[mid]) < 0)
			{
				hi = mid - 1;
			}
			else if (p.compareTo(points[mid]) > 0)
			{
				lo = mid + 1;
			}
			else
			{
				return mid;
			}
		}
		return -1;
	}

	private static int findMinIndex(Point[] points, int pointIndex)
	{
		int minIndex = pointIndex;
		for (; minIndex >= 0; --minIndex)
		{
			if (points[pointIndex].compareTo(points[minIndex]) != 0)
			{
				break;
			}
		}
		return minIndex;
	}

	private static int findMaxIndex(Point[] points, int pointIndex)
	{
		int maxIndex = pointIndex;
		for (; maxIndex < points.length; ++maxIndex)
		{
			if (points[pointIndex].compareTo(points[maxIndex]) != 0)
			{
				break;
			}
		}
		return maxIndex;
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
