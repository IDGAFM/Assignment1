package org.example;

import org.example.util.Metrics;

/**
 * Deterministic selection (Median of Medians) — возвращает k-th (0-based) минимальный элемент в массиве.
 * Интеграция с Metrics: incComparisons() вызывается при сравнениях, incCopies() при swap/copy.
 *
 * Алгоритм:
 *  - Разбиваем на группы по 5, сортируем каждую группу, перемещаем медианы в начало.
 *  - Рекурсивно находим медиану медиан (через selectIndex) — это индекс pivot.
 *  - Делим массив вокруг pivot и рекурсируем в соответствующую половину.
 *
 * Важное: public API использует 0-based k.
 */
public class DeterministicSelect {

    // public API: возвращает значение k-th (0-based)
    public static int selectKth(int[] a, int k, Metrics m) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("empty");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of bounds");
        int idx = selectIndex(a, 0, a.length - 1, k, m);
        return a[idx];
    }

    /**
     * Возвращает индекс элемента, который является k-th (0-based) в a[lo..hi].
     * Здесь k — глобальный индекс в массиве (то есть изначально 0..n-1).
     */
    private static int selectIndex(int[] a, int lo, int hi, int kIndex, Metrics m) {
        while (true) {
            if (lo == hi) return lo;

            // выбираем индекс опоры детерминированно
            int pivotIndex = medianOfMediansIndex(a, lo, hi, m);

            // partition вокруг pivot и получаем финальную позицию pivot
            pivotIndex = partitionAroundPivot(a, lo, hi, pivotIndex, m);

            if (kIndex == pivotIndex) {
                return kIndex;
            } else if (kIndex < pivotIndex) {
                hi = pivotIndex - 1;
            } else {
                lo = pivotIndex + 1;
            }
        }
    }

    /**
     * Возвращает индекс медианы медиан в диапазоне a[lo..hi].
     * То есть индекс элемента, который будет использоваться как опора.
     */
    private static int medianOfMediansIndex(int[] a, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        if (n <= 5) {
            insertionSort(a, lo, hi, m);
            return lo + n / 2;
        }

        int numMedians = 0;
        for (int i = lo; i <= hi; i += 5) {
            int subR = Math.min(i + 4, hi);
            insertionSort(a, i, subR, m);
            int medianIndex = i + (subR - i) / 2;
            swap(a, lo + numMedians, medianIndex, m);
            numMedians++;
        }

        // теперь медианы лежат в [lo .. lo+numMedians-1]
        int midOffset = numMedians / 2;
        // вызываем selectIndex с kIndex = lo + midOffset (глобальный индекс!)
        return selectIndex(a, lo, lo + numMedians - 1, lo + midOffset, m);
    }

    /**
     * Partition around pivotIndex: places pivot at its final position (store),
     * all elements < pivot to left, >= pivot to right. Возвращает final index of pivot.
     * (Используется сравнение < pivot для устойчивости по отношению к дубликатам.)
     */
    private static int partitionAroundPivot(int[] a, int lo, int hi, int pivotIndex, Metrics m) {
        int pivot = a[pivotIndex];
        swap(a, pivotIndex, hi, m); // move pivot to end
        int store = lo;
        for (int i = lo; i < hi; i++) {
            m.incComparisons();
            if (a[i] < pivot) {
                swap(a, store++, i, m);
            }
        }
        swap(a, store, hi, m); // move pivot to its final place
        return store;
    }

    // insertion sort on a[l..r], updates metrics
    private static void insertionSort(int[] a, int l, int r, Metrics m) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= l) {
                m.incComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    m.incCopies();
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = key;
            m.incCopies();
        }
    }

    // swap with metrics accounting
    private static void swap(int[] a, int i, int j, Metrics m) {
        if (i == j) return;
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
        m.incCopies(); // count swap as a copy operation (or two copies — but we use one increment)
    }
}
