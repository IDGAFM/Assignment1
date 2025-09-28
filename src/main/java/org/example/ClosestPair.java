package org.example;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    public static double closest(Point[] pts) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        Point[] Px = pts.clone();
        Point[] Py = pts.clone();
        Arrays.sort(Px, Comparator.comparingDouble(p -> p.x));
        Arrays.sort(Py, Comparator.comparingDouble(p -> p.y));
        return closestRec(Px, 0, Px.length - 1, Py);
    }

    private static double closestRec(Point[] Px, int l, int r, Point[] Py) {
        int n = r - l + 1;
        if (n <= 3) return bruteForce(Px, l, r);
        int mid = (l + r) / 2;
        Point midPoint = Px[mid];

        Point[] Pyl = new Point[mid - l + 1];
        Point[] Pyr = new Point[r - mid];
        int li = 0, ri = 0;
        for (Point p : Py) {
            if (p.x <= midPoint.x && li < Pyl.length) Pyl[li++] = p;
            else Pyr[ri++] = p;
        }

        double dl = closestRec(Px, l, mid, Pyl);
        double dr = closestRec(Px, mid + 1, r, Pyr);
        double d = Math.min(dl, dr);

        Point[] strip = new Point[Py.length];
        int m = 0;
        for (Point p : Py) {
            if (Math.abs(p.x - midPoint.x) < d) strip[m++] = p;
        }

        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m && (strip[j].y - strip[i].y) < d; j++) {
                d = Math.min(d, dist(strip[i], strip[j]));
            }
        }
        return d;
    }

    private static double bruteForce(Point[] a, int l, int r) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = l; i <= r; i++)
            for (int j = i + 1; j <= r; j++)
                best = Math.min(best, dist(a[i], a[j]));
        return best;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }
}
