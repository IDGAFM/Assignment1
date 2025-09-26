package org.example.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter implements AutoCloseable {
    private final BufferedWriter w;

    public CSVWriter(String path, boolean append) throws IOException {
        File f = new File(path);
        File parent = f.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();
        w = new BufferedWriter(new FileWriter(f, append));
    }

    public void writeHeader() throws IOException {
        w.write("algo,n,trial,time_ms,max_depth,comparisons,copies\n");
    }

    public void writeRow(String algo, int n, int trial, long timeMs, int maxDepth, long comparisons, long copies) throws IOException {
        w.write(String.format("%s,%d,%d,%d,%d,%d,%d\n", algo, n, trial, timeMs, maxDepth, comparisons, copies));
    }

    @Override
    public void close() throws IOException { w.close(); }
}
