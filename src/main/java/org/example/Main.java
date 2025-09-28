package org.example;

import org.example.Point;
import org.example.ClosestPair;
import org.example.util.CSVWriter;
import org.example.util.Metrics;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        // Simple CLI: args: <algo> <n> <trials> <out.csv>
        if (args.length < 4) {
            System.out.println("Usage: <algo> <n> <trials> <out.csv>");
            System.out.println("algo: mergesort | quicksort | select | closest");
            return;
        }
        String algo = args[0];
        int n = Integer.parseInt(args[1]);
        int trials = Integer.parseInt(args[2]);
        String out = args[3];

        try (CSVWriter w = new CSVWriter(out, false)) {
            w.writeHeader();
            Random rnd = new Random(12345);
            for (int t = 0; t < trials; t++) {
                Metrics m = new Metrics();
                if (algo.equals("mergesort")) {
                    int[] a = randomArray(n, rnd);
                    m.startTime();
                    MergeSort.sort(a, m);
                    long time = m.StopTimer();
                    w.writeRow("mergesort", n, t, time, m.getMaxDepth(), m.getComparisons(), m.getCopies());
                } else if (algo.equals("quicksort")) {
                    int[] a = randomArray(n, rnd);
                    m.startTime();
                    QuickSort.sort(a, m);
                    long time = m.StopTimer();
                    w.writeRow("quicksort", n, t, time, m.getMaxDepth(), m.getComparisons(), m.getCopies());
                } else if (algo.equals("select")) {
                    int[] a = randomArray(n, rnd);
                    int k = Math.max(0, n / 2);
                    m.startTime();
                    int val = DeterministicSelect.selectKth(a, k, m);
                    long time = m.StopTimer();
                    w.writeRow("select", n, t, time, m.getMaxDepth(), m.getComparisons(), m.getCopies());
                } else if (algo.equals("closest")) {
                    Point[] pts = randomPoints(n, rnd);
                    m.startTime();
                    double d = ClosestPair.closest(pts);
                    long time = m.StopTimer();
                    w.writeRow("closest", n, t, time, m.getMaxDepth(), m.getComparisons(), m.getCopies());
                } else {
                    System.out.println("Unknown algo: " + algo);
                    return;
                }
            }
        }
    }

    private static int[] randomArray(int n, Random rnd) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt();
        return a;
    }
    private static Point[] randomPoints(int n, Random rnd) {
        Point[] p = new Point[n];
        for (int i = 0; i < n; i++) p[i] = new Point(rnd.nextDouble(), rnd.nextDouble());
        return p;
    }
}
