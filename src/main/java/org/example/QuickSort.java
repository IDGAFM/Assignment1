package org.example;

import org.example.util.Metrics;

import java.util.Random;

public class QuickSort {
    private static final ThreadLocal<Random> RNG = ThreadLocal.withInitial(() -> new Random(12345));

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length < 2) return;
        m.enterRecursion();
        try {
            quicksort(a, 0, a.length - 1, m);
        } finally {
            m.exitRecursion();
        }
    }

    private static void quicksort(int[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            int pivotIndex = lo + RNG.get().nextInt(hi - lo + 1);
            int p = partition(a, lo, hi, pivotIndex, m);
            int leftSize = p - lo;
            int rightSize = hi - p;
            // recurse on smaller side to guarantee stack bound
            if (leftSize < rightSize) {
                if (leftSize > 0) {
                    m.enterRecursion();
                    try {
                        quicksort(a, lo, p - 1, m);
                    } finally { m.exitRecursion(); }
                }
                lo = p + 1; // iterate on larger side
            } else {
                if (rightSize > 0) {
                    m.enterRecursion();
                    try {
                        quicksort(a, p + 1, hi, m);
                    } finally { m.exitRecursion(); }
                }
                hi = p - 1;
            }
        }
    }

    private static int partition(int[] a, int lo, int hi, int pivotIndex, Metrics m) {
        int pivot = a[pivotIndex];
        swap(a, pivotIndex, hi); m.incCopies();
        int i = lo;
        for (int j = lo; j < hi; j++) {
            m.incComparisons();
            if (a[j] <= pivot) {
                swap(a, i++, j); m.incCopies();
            }
        }
        swap(a, i, hi); m.incCopies();
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
}
