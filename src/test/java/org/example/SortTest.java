package org.example;

import org.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SortTest {
    @Test
    public void testMergeSortRandom() {
        int n = 1000;
        int[] a = new int[n];
        Random r = new Random(42);
        for (int i = 0; i < n; i++) a[i] = r.nextInt();
        int[] copy = Arrays.copyOf(a, a.length);
        Metrics m = new Metrics();
        MergeSort.sort(a, m);
        Arrays.sort(copy);
        assertArrayEquals(copy, a);
    }

    @Test
    public void testQuickSortDepthAndCorrectness() {
        int n = 1 << 12; // 4096
        int[] a = new int[n];
        Random r = new Random(123);
        for (int i = 0; i < n; i++) a[i] = r.nextInt();
        Metrics m = new Metrics();
        QuickSort.sort(a, m);
        for (int i = 1; i < n; i++) assertTrue(a[i-1] <= a[i]);
        int bound = 2 * (int)Math.floor(Math.log(n)/Math.log(2)) + 50;
        assertTrue(m.getMaxDepth() <= bound, "QS depth exceeded: " + m.getMaxDepth());
    }
}
