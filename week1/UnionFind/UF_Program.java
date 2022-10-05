import edu.princeton.cs.algs4.*;

class UnionFind {
    private int[] id;
    private int[] sz;
    public UnionFind(int N) {
        id = new int[N];
        sz = new int[N];
        // init the same array of elements
        UF_Program.initArr(id);
        // this array shows how many elements each element stores as a root
        // for example: sz[3] = 8 means element three is root for 8 elemtns
        // in start each element stores only itself
        for (var i = 0; i < sz.length; i++) {
            sz[i] = 1;
        }
    }

    // find root of element i
    private int root(int i) {
        // root stores only itself
        while (i != id[i]) {
            id[i] = id[id[i]]; // compression
            i = id[i]; // step up 
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        // attaching a smaller tree to a larger one
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        }
        else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }

    public void printId() {
        UF_Program.printArr(id);
    }
    public void printSize() {
        UF_Program.printArr(sz);
    }

    /**
     * Recursive search for the maximum element in a set that includes {@code elem}
     * @param elem - any element in set
     * @return The largest element in the set
     */
    public int FindMaxValue(int elem) {
        int max = Rfind(root(elem));
        return max;
    }

    /**
     * Recursive search the maximum value in connected elements 
     * @param root of element which get like a param in pre function
     * @return the maximum value in set 
     */
    private int Rfind(int root) {
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < id.length; i++)
        {
            if (root == i) 
                continue;
            if (root == id[i])
            {
                // of course you can use Math.max() instead of line down
                result = (i > root)? i : root;
                Rfind(i);
            }
        }
        return result;
    }
}

public class UF_Program {
    // initial array length N values {0, 1, 2, ..., n - 2, n - 1}
    public static void initArr(int... nums) {
        try {
            for (var i = 0; i < nums.length; i++) {
                nums[i] = i;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // print array length N like {0, 1, 2, ..., n - 2, n - 1}
    public static void printArr(int... nums) {
        System.out.print("{");
        for (var i : nums) {
            System.out.printf(" %d; ", i);
        }
        System.out.print("}");
    }

    final static int N = 10;

    public static void main(String[] args) {
        // create structure
        UnionFind uFind = new UnionFind(N);

        // to do operations with structure
        // N>>1 = N/2
        for (int i = 0; i < N>>1; i++) {
            int a = StdRandom.uniformInt(N);
            int b = StdRandom.uniformInt(N);
            if (!uFind.connected(a, b))
                uFind.union(a, b);
            System.out.print("Array:\t");
            uFind.printId();
            System.out.println("\ta: " + a + "\tb: " + b);
            System.out.print("Size: \t");
            uFind.printSize();
            System.out.println();
        }

        System.out.print("Print num: ");
        int n = StdIn.readInt();
        System.out.printf("Max num in set included %d is %d.\n", n, uFind.FindMaxValue(n));
    }
}