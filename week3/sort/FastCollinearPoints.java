import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> ls = new ArrayList<>();

    public FastCollinearPoints(Point[] points) // находит все сегменты линии, содержащие 4 или более точек
    {
        if (points == null)
            throw new IllegalArgumentException();

        Point[] pCopy = points.clone();
        Arrays.sort(pCopy);

        if (hasDuplicate(pCopy)) {
            throw new IllegalArgumentException("U have duplicate points");
        }

        for (int i = 0; i < pCopy.length - 3; i++) {
            Arrays.sort(pCopy);

            // Sort the points according to the slopes they makes with p.
            // Check if any 3 (or more) adjacent points in the sorted order
            // have equal slopes with respect to p. If so, these points,
            // together with p, are collinear.

            Arrays.sort(pCopy, pCopy[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < pCopy.length; last++) {
                // find last collinear to p point
                while (last < pCopy.length
                        && Double.compare(pCopy[p].slopeTo(pCopy[first]), pCopy[p].slopeTo(pCopy[last])) == 0) {
                    last++;
                }
                // if found at least 3 elements, make segment if it's unique
                if (last - first >= 3 && pCopy[p].compareTo(pCopy[first]) < 0) {
                    ls.add(new LineSegment(pCopy[p], pCopy[last - 1]));
                }
                // Try to find next
                first = last;
            }
        }
    }

    // test the whole array fo duplicate points
    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

    public int numberOfSegments() // количество сегментов линии
    {
        return ls.size();
    }

    public LineSegment[] segments() // сегменты линии
    {
        return ls.toArray(new LineSegment[ls.size()]);
    }
}
