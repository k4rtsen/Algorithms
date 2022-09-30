class UnionFind {
    private int[] id;
    private int[] sz;

    public UnionFind(int N) {
        id = new int[N];
        sz = new int[N];
        Program.initArr(id);
        for (var i = 0; i < sz.length; i++) {
            sz[i] = 1;
        }
    }

    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
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
        Program.printArr(id);
    }
    public void printSize() {
        Program.printArr(sz);
    }
}

public class Program {
    final static int N = 10;

    public static void main(String[] args) {
        UnionFind uFind = new UnionFind(N);
        for (int i = 0; i < N; i++) {
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
        }
    }

    public static void initArr(int... nums) {
        try {
            for (var i = 0; i < nums.length; i++) {
                nums[i] = i;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void printArr(int... nums) {
        System.out.printf("{");
        for (var i : nums) {
            System.out.printf(" %d; ", i);
        }
        System.out.printf("}");
    }
}