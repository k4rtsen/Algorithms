import java.lang.Comparable;
import java.util.Random;

public class ElementarySorting {

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    private static boolean isSorted(Comparable a[]) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1]))
                return false;
        return true;
    }

    public static void SelectionSort(Comparable a[]) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++)
                if (less(a[j], a[min]))
                    min = j;
            exch(a, i, min);
        }
    }

    public static void InsertionSort(Comparable a[]) {
        int N = a.length;
        for (int i = 0; i < N; i++)
            for (int j = i; j > 0; j--)
                if (a[j].compareTo(a[j - 1]) < 0) {
                    exch(a, j, j - 1);
                } else
                    break;
    }

    public static void ShellSort(Comparable a[]) {
        int N = a.length;
        int h = 1;
        while (h < N / 3)
            h = 3 * h + 1; // 1, 4, 13, 40, 121, 364, ...
        while (h >= 1) { // h-sort the array.
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h)
                    exch(a, j, j - h);
            }

            h = h / 3;
        }
    }

    public static void main(String[] args) {
        final int N = 1024 * 1024;
        Integer arr[] = new Integer[N];

        // String or Integer
        Random rand = new Random(); // для рандомных целых чисел в диапазоне [0..N)
        for (int i = 0; i < N; i++) {
            arr[i] = rand.nextInt(N);
        }

        if (isSorted(arr))
            System.out.println("Sorted");
        else {

            System.out.println("---Sorting---");
            InsertionSort(arr);

            if (isSorted(arr))
                System.out.println("Sorted");
            else
                System.out.println("Error!");
        }
    }
}
