package com.company.homework4;

/**
 * Created by Lomdji on 24.12.2016.
 */
import java.util.concurrent.TimeUnit;

import static com.company.homework4.MatrixGenerationUtils.*;

public class MatrixGenerationTests {

    public static void main(String[] args) {
        int[] rowSizes = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 20 };
        int cols = 10_000_000;

        warmup();

        System.out.println("cols = " + cols);
        System.out.println("rows     time1(ms, non-par)    time2(ms, par)   time2 / time1 (less -> better)");
        for (int rows : rowSizes) {
            long time1 = mesureTimeNs(() -> generateMatrix(rows, cols));
            long time2 = mesureTimeNs(() -> generateMatrixParallelRows(rows, cols));
            long time1Ms = TimeUnit.NANOSECONDS.toMillis(time1);
            long time2Ms = TimeUnit.NANOSECONDS.toMillis(time2);
            float ratio = ((float)time2) / time1;

            System.out.format("%2d    %17d   %17d       %f\n", rows, time1Ms, time2Ms, ratio);
        }
    }

    /* DO NOT READ THIS, i will explain later*/
    private static void warmup() {
        System.out.println("Warming up start");
        for (int i = 0; i < 100; i++) {
            if(i % 20 == 0)
                System.out.println(i + "%");
            generateMatrix(4, 100_000);
            generateMatrixParallelRows(2, 100_000);
        }
        System.out.println("Warming up end\n");
    }


    private static long mesureTimeNs(Runnable runnable) {
        long timeStart = System.nanoTime();
        runnable.run();
        return System.nanoTime() - timeStart;
    }

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
            threads[i] = new Thread() {
                @Override
                public void run() {
                    processRow(row);
                }
            };
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