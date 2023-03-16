import java.util.Random;

public class MargeSort {

    /**
     * Compare v and w
     * @param v - one elem
     * @param w - another elem
     * @return {@code true } if (v < w) and {@code false } otherwise
     */
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    /**
     * Swap elements a[i] to a[j]
     * @param a[] - the comparable array
     * @param i - one element
     * @param j - the other element
     */
    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    /**
     * 
     * @param a [] - the comparable array
     * @param l - the first element
     * @param r - the last element
     * @return {@code true } if a is sorted and {@code false } otherwise
     */
    private static boolean isSorted(Comparable a[], int l, int r) {
        for (int i = l + 1; i < r; i++)
            if (less(a[i], a[i - 1]))
                return false;
        return true;
    }

    /**
     * Insertion sorting method
     * @param a [] - the comparable array
     * @param l - the first element
     * @param r - the last element
     */
    public static void InsertionSort(Comparable a[], int l, int r) {
        for (int i = l; i < r; i++)
            for (int j = i; j > l; j--)
                if (a[j].compareTo(a[j - 1]) < 0)
                    exch(a, j, j - 1);
                else
                    break;
    }

    /**
     * aux[1][3][6] & aux[2][4][5] --> a[1][2][3][4][5][6]
     * @param a [] - the comparable array
     * @param aux [] - part-sorted array with elements from the comparable array a[]
     * @param lo - the first element in part of a[]
     * @param mid - the middle element in part of a[]
     * @param hi - the last element in part of a[]
     */
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);
        // здесь бы очень подошел queue
        for (int k = lo; k <= hi; k++)
            // копируем левую и правую часть массива в отдельный
            aux[k] = a[k];
        int i = lo, j = mid + 1;
        // и из него, уже сравнивая поэлементно, выписываем в основной массив
        //
        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                // если в aux[lo .. mid] перебрали все элементы
                a[k] = aux[j++];
            else if (j > hi)
                // если в aux[mid + 1 .. hi] перебрали все элементы
                a[k] = aux[i++];
            else if (less(aux[j], aux[i]))
                // из стека aux[j] -> a[k], потому что aux[j] < aux[i]
                // также увеличиваем счетчик j на единицу
                a[k] = aux[j++];
            else
                // и наоборот
                a[k] = aux[i++];
        }
        assert isSorted(a, lo, hi);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        final int CUTOFF = 7;
        if (hi <= lo + CUTOFF - 1) {
            // если мало элементов (~7) то используем сортировку вставками
            // данный процесс увеличивает скорость на 25%, т.к. сортировка объединением
            // медленна при малых размерах массива
            InsertionSort(a, lo, hi + 1);
            return;
        }

        int mid = lo + (hi - lo) / 2; // каждый раз делим размер массива наполовину
        sort(a, aux, lo, mid); // делаем тоже самое с первой половиной получаемого массива
        sort(a, aux, mid + 1, hi); // и так же со второй
        // код выше уже сортирует отдельные части массива и если конечный элемент левой
        // части меньше конечного элемента правой части, то обе части массива
        // отсортированы и объединения не требует
        if (!less(a[mid + 1], a[mid]))
            return;
        // объединяем левую и правую части массива (уже отсортированные)
        merge(a, aux, lo, mid, hi);
    }

    /**
     * Merge-sorting method
     * @param a [] - the comparable array
     */
    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    /**
     * Точно такое же объединение, только уже без рекурсии и если размер массива есть число, равное степени двойки
     * @param a [] - the comparable array
     */
    public static void Bottom_Up_sort(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz + sz)
            for (int lo = 0; lo < N - sz; lo += sz + sz)
                merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
    }

    /**
     * Print array in console
     * @param arr [] - the iterable array
     */
    public static void printArr(Integer[] arr) {
        System.out.print("[");
        for (var val : arr)
            System.out.print(val + "; ");
        System.out.print("]\n");
    }

    public static void main(String[] args) {
        final int N = 1024 * 1024; // 2^10 * 2^10
        Integer arr[] = new Integer[N]; // size = 2^20

        // fill with random nums
        Random rand = new Random();
        for (int i = 0; i < N; i++)
            arr[i] = rand.nextInt(N);

        if (isSorted(arr, 0, arr.length))
            System.out.println("Sorted");
        else {
            System.out.println("---Sorting---");
            sort(arr);

            if (isSorted(arr, 0, arr.length))
                System.out.println("Sorted");
            else
                System.out.println("Error!");
        }
    }
}
