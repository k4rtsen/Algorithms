import java.util.Scanner;

class UnionFind {
    private int[] id;
    private int[] sz;
    private int[] max_val;

    public UnionFind(int N) {
        id = new int[N];
        sz = new int[N];
        max_val = new int[N];
        // init the same array of elements
        Program.initArr(id);
        Program.initArr(max_val);
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
            storedMaxVal(i, j);
            sz[j] += sz[i];
        }
        else {
            id[j] = i;
            storedMaxVal(i, j);
            sz[i] += sz[j];
        }
    }

    private void storedMaxVal(int i, int j) {
        if (max_val[i] > max_val[j])
            max_val[j] = max_val[i];
        else
            max_val[i] = max_val[j];
    }

    public void printId() {
        Program.printArr(id);
    }
    public void printSize() {
        Program.printArr(sz);
    }
    public void printMaxVal() {
        Program.printArr(max_val);
    }

    public int FindMaxValue(int elem) {
        int max = find(root(elem));
        return max;
    }

    /**
     * Recursive search the maximum value in connected elements 
     * @param root of element which get like a param in pre function
     * @return the maximum value in connected elements 
     */
    private int find(int root) {
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < id.length; i++)
        {
            if (root == i) 
                continue;
            if (root == id[i])
            {
                result = (i > root)? i : root;
                find(i);
            }
        }
        return result;
    }
}

public class Program {
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
        System.out.printf("{");
        for (var i : nums) {
            System.out.printf(" %d; ", i);
        }
        System.out.printf("}");
    }

    final static int N = 10;

    public static void main(String[] args) {
        // create structure
        UnionFind uFind = new UnionFind(N);

        // to do operations with structure
        for (int i = 0; i < N>>1; i++) {
            int a = (int) (Math.random() * N);
            int b = (int) (Math.random() * N);
            if (!uFind.connected(a, b))
                uFind.union(a, b);
            System.out.printf("Array:\t");
            uFind.printId();
            System.out.println("\ta: " + a + "\tb: " + b);
            System.out.printf("Size: \t");
            uFind.printSize();
            System.out.println();
            System.out.printf("Max: \t");
            uFind.printMaxVal();
            System.out.println();
        }

        Scanner in = new Scanner(System.in);
        System.out.print("Print num: ");
        int n = in.nextInt();
        System.out.printf("Max num in %d is %d.\n", n, uFind.FindMaxValue(n));
        in.close();
    }
}