package org.example;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ClosestTest {
    private double bruteForce(Point[] pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                best = Math.min(best, Math.hypot(dx, dy));
            }
        }
        return best;
    }

    @Test
    public void testClosestPairSmall() {
        Random rnd = new Random(123);
        for (int t = 0; t < 20; t++) {
            int n = 200 + rnd.nextInt(1800);
            Point[] pts = new Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new Point(rnd.nextDouble(), rnd.nextDouble());
            }
            double d1 = ClosestPair.closest(pts);
            double d2 = bruteForce(pts);
            assertEquals(d2, d1, 1e-9);
        }
    }

    @Test
    public void testClosestPairTrivial() {
        Point[] pts = new Point[] {
                new Point(0, 0),
                new Point(3, 4),
                new Point(0, 1)
        };
        double d = ClosestPair.closest(pts);
        assertEquals(1.0, d, 1e-9);
    }
}
