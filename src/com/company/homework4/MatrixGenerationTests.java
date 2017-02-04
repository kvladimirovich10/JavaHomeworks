package com.company.homework4;

/**
 * Created by Lomdji on 24.12.2016.
 */

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.company.homework4.MatrixGenerationUtils.*;

public class MatrixGenerationTests {

    public static void main(String[] args) {
        int[] rowSizes = new int[]{2, 4, 6, 8, 10};
        int[] threadCounts = new int[]{1, 2, 3, 4, 8, 20};
        int cols = 300_000;

        warmup();

        for (int threadCount : threadCounts) {
            System.out.println("cols        = " + cols);
            System.out.println("threadCount = " + threadCount);
            System.out.println("rows     time1(ms, non-par)    time2(ms, par)   time2 / time1 (less -> better)");

            for (int rows : rowSizes) {
                long time1 = measureTimeNonParralell(rows, cols);
                long time2 = measureTimeParralell(rows, cols, threadCount);
                long time1Ms = TimeUnit.NANOSECONDS.toMillis(time1);
                long time2Ms = TimeUnit.NANOSECONDS.toMillis(time2);
                float ratio = ((float) time2) / time1;

                System.out.format("%2d    %17d   %17d       %f\n", rows, time1Ms, time2Ms, ratio);
            }

            System.out.println();
        }
    }

    private static long measureTimeParralell(int rows, int cols, int threadCount) {
        return measureTimeNs(() -> generateMatrixParallelRows(rows, cols, threadCount));
    }

    private static final HashMap<Integer, Long> cache = new HashMap<>();

    /* If we already measured time for dim=rows x cols then take the value from the cache */
    private static long measureTimeNonParralell(int rows, int cols) {
        long time1;
        if (cache.containsKey(rows)) {
            time1 = cache.get(rows);
        } else {
            time1 = measureTimeNs(() -> generateMatrix(rows, cols));
            cache.put(rows, time1);
        }
        return time1;
    }

    /* DO NOT READ THIS, i will explain later*/
    private static void warmup() {
        System.out.println("Warming up start");
        for (int i = 0; i < 100; i++) {
            if (i % 20 == 0)
                System.out.print(i + "% ");
            generateMatrix(4, 10_000);
            generateMatrixParallelRows(2, 10_000, 2);
        }
        System.out.println();
        System.out.println("Warming up end\n");
    }


    private static long measureTimeNs(Runnable runnable) {
        long timeStart = System.nanoTime();
        runnable.run();
        return System.nanoTime() - timeStart;
    }




    /************************************************
     *
     * Other stuff, not needed for lab
     *
     *************************************************/
    private static void exampleProcessMatrixParallel() {
        double[][] matrix = generateMatrix(10, 10_000_000);
        long start = System.nanoTime();
        processMatrix(matrix);
        long stop = System.nanoTime();

        System.out.println("Elapsed = " + (stop - start));
    }

    private static void processMatrix(double[][] matrix) {
        Thread[] threads = new Thread[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            final double[] row = matrix[i];
            threads[i] = new Thread(() -> processRow(row));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void processRow(double[] ds) {
        for (int i = 0; i < ds.length; i++) {
            Math.pow(ds[i], ds[i]);
        }
    }

}