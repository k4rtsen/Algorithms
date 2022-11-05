import edu.princeton.cs.algs4.StopwatchCPU;
import edu.princeton.cs.algs4.StdRandom;

public class ThreeSum {
    // O(1/6*N^3) in worst case
    public static int BruteForce(int[] a) {
        final int N = a.length;
        int count = 0;
        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++)
                for (int k = j + 1; k < a.length; k++)
                    if (a[i] + a[j] + a[k] == 0)
                        count++;
        return count;
    }
    static final int N = 100;
    public static void main(String[] args) {
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = StdRandom.uniformInt(-100, 100);   
        }
        for (int i : a) {
            System.out.print(i + ", ");
        }
        StopwatchCPU timer = new StopwatchCPU();
        System.out.println("\n" + BruteForce(a));
        double time = timer.elapsedTime();
        System.out.println(time);
    }
}