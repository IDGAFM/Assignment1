package org.example;

import org.example.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SelectTest {
    @Test
    public void testSelectAgainstSort() {
        Random r = new Random(12345);
        int trials = 100;
        for (int t = 0; t < trials; t++) {
            int n = 100 + r.nextInt(1900);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = r.nextInt(1_000_000);
            int k = r.nextInt(n); // индекс в [0..n-1]

            int[] copy = Arrays.copyOf(a, a.length);
            Arrays.sort(copy);

            Metrics m = new Metrics();
            int res = DeterministicSelect.selectKth(a, k, m);

            assertEquals(copy[k], res);
        }
    }
}
