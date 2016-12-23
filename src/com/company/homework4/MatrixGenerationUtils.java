package com.company.homework4;
import java.util.Random;

/**
 * Created by Lomdji on 24.12.2016.
 */

public class MatrixGenerationUtils {

    public static double[][] generateMatrix(int rows, int cols) {
        Random random = new Random();
        double[][] matrix = new double[rows][cols];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                matrix[i][j] = random.nextDouble();

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
    public static double[][] generateMatrixParallelRows(int rows, int cols, int threadsCount) {
        // TODO: place your code here
        Random random = new Random();
        double[][] matrix = new double[rows][cols];


        return null;
    }

    public static double[][] generateMatrixParallelRows(int rows, int cols) {
        return generateMatrixParallelRows(rows, cols, 1);
    }

}
