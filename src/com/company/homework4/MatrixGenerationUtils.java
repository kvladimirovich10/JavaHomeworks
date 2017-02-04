package com.company.homework4;

import java.util.Random;

/**
 * Created by Lomdji on 24.12.2016.
 */

public class MatrixGenerationUtils {

    /****************************************************
     * USE THIS METHOD FOR RANDOM GENERATION OF VALUE!
     ****************************************************/
     static double randomValue(Random random) {
        double sum = 0;
        int iterations = 20;
        double MAX_VALUE = Double.MAX_VALUE / iterations;
        for (int i = 0; i < iterations; i++) {
            sum += random.nextDouble() % MAX_VALUE;
        }
        return sum;
    }

     public static double[][] generateMatrix(int rows, int cols) {
        Random random = new Random();

        double[][] matrix = new double[rows][cols];

        for (int i = 0; i < matrix.length;i++)
            for (int j = 0; j < matrix[i].length; j++)
                matrix[i][j] = randomValue(random);

        return matrix;
    }


    /**
     * Generate matrix in multiple threads
     *
     * @param rows         matrix rows number
     * @param cols         matrix cols number
     * @param threadsCount number of threads to use during generation
     * @return generated matrix
     */
    //Version Consumer-Conveyor;
    public static double[][] generateMatrixParallelRows(int rows, int cols, int threadsCount) {
        // TODO: place your code here
        //Random random = new Random();
        double[][] matrix = new double[rows][cols];
        Conveyor conveyer = new Conveyor(matrix);
        Consumer[] consumer = new Consumer[threadsCount];
        for (int i = 0; i < consumer.length; i++) {
            consumer[i] = new Consumer(conveyer);
            consumer[i].start();
        }
        for (Consumer cons : consumer) {
            try {
                cons.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return matrix;
    }
}