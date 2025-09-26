package org.example;

import org.example.util.Metrics;

public class MergeSort {
    private static final int INSERTION_CUTOFF = 16;

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length < 2) return;
        int[] buf = new int[a.length];
        m.enterRecursion();
        try {
            sort(a, 0, a.length - 1, buf, m);
        } finally {
            m.exitRecursion();
        }
    }

    private static void sort(int[] a, int l, int r, int[] buf, Metrics m) {
        if (r - l + 1 <= INSERTION_CUTOFF) {
            insertionSort(a, l, r, m);
            return;
        }
        int mid = l + (r - l) / 2;
        sort(a, l, mid, buf, m);
        sort(a, mid + 1, r, buf, m);
        merge(a, l, mid, r, buf, m);
        System.arraycopy(buf, l, a, l, r - l + 1);
    }

    private static void merge(int[] a, int l, int mid, int r, int[] buf, Metrics m) {
        int i = l, j = mid + 1, k = l;
        while (i <= mid && j <= r) {
            m.incComparisons();
            if (a[i] <= a[j]) {
                buf[k++] = a[i++]; m.incCopies();
            } else {
                buf[k++] = a[j++]; m.incCopies();
            }
        }
        while (i <= mid) { buf[k++] = a[i++]; m.incCopies(); }
        while (j <= r) { buf[k++] = a[j++]; m.incCopies(); }
    }

    private static void insertionSort(int[] a, int l, int r, Metrics m) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= l) {
                m.incComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j]; m.incCopies();
                    j--;
                } else break;
            }
            a[j + 1] = key; m.incCopies();
        }
    }
}
