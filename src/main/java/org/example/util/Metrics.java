package org.example.util;

import java.util.concurrent.TimeUnit;

public class Metrics {
    private long comparisons = 0;
    private long copies = 0;
    private int currentDepth = 0;
    private int maxDepth = 0;
    private long startTimeNanos = 0;


    public synchronized void intComparisons() { comparisons++; }
    public synchronized void intCopies() { copies++; }


    public synchronized void enterRecursion(){
        currentDepth++;
        if (currentDepth > maxDepth) maxDepth = currentDepth;
    }


    public synchronized void exitRecursion(){
        currentDepth = Math.max(0, currentDepth -1);
    }


    public void startTime() { startTimeNanos = System.nanoTime(); }
    public long StopTimer(){
        if (startTimeNanos == 0) return 0;
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTimeNanos);
    }


    public long getComparisons() { return comparisons; }
    public long getCopies() { return copies; }
    public int getMaxDepth() { return maxDepth; }


    public void reset(){
        comparisons = 0;
        copies = 0;
        currentDepth = 0;
        maxDepth = 0;
        startTimeNanos = 0;
    }
}
