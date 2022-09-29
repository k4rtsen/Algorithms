class UnionFind {
    private int[] id;

    public UnionFind(int N) {
        id = new int[N];
        Program.initArr(id);
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++)
            if (id[i] == pid)
                id[i] = qid;
    }

    public void print() {
        Program.printArr(id);
    }
}

public class Program {
    final static int N = 20;

    public static void main(String[] args) {
        UnionFind uFind = new UnionFind(N);
        for (int i = 0; i < N; i++) {
            int a = (int) (Math.random() * N);
            int b = (int) (Math.random() * N);
            if (!uFind.connected(a, b))
                uFind.union(a, b);
            uFind.print();
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