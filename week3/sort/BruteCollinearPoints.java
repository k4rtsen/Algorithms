import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> ls = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) // находит все сегменты линии, содержащие 4 точки
    {
        if (points == null)
            throw new IllegalArgumentException();

        Point[] pCopy = points.clone();
        Arrays.sort(pCopy);

        if (hasDuplicate(pCopy)) {
            throw new IllegalArgumentException("U have duplicate points");
        }

        for (int i = 0; i < pCopy.length - 3; i++)
            for (int j = i + 1; j < pCopy.length - 2; j++)
                for (int k = j + 1; k < pCopy.length - 1; k++)
                    for (int l = k + 1; l < pCopy.length; l++)
                        if (pCopy[i].slopeTo(pCopy[j]) == 0)
                            if (pCopy[i].slopeTo(pCopy[k]) == 0)
                                if (pCopy[i].slopeTo(pCopy[l]) == 0)
                                    ls.add(new LineSegment(pCopy[i], pCopy[l]));
    }

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

    public static void main(String[] args) {

    }
}
