package com.company.homework2;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by lomdji on 28.10.2016.
 */
public class Matrix implements Serializable {
    protected int width;
    protected int height;
    protected double[][] matr;

    Matrix(int height, int width, double value) {
        if (height == 0 || width == 0)
            throw new MatrixException("Error constructor arguments");
        this.width = width;
        this.height = height;
        matr = new double[height][width];
        for (int i = 0; i != height; i++)
            for (int j = 0; j != width; j++)
                matr[i][j] = value;
    }

    Matrix(int Size, double value) {
        this(Size, Size, value);
    }

    protected Matrix() {
    }

    //Constructor
    Matrix(int height, int width, double[][] value) throws MatrixException {
        if (height == 0 || width == 0 || value.length < height || value[0].length < width)
            throw new MatrixException("Error constructor arguments");
        this.width = width;
        this.height = height;
        matr = new double[height][width];
        for (int i = 0; i != height; i++)
            for (int j = 0; j != width; j++)
                matr[i][j] = value[i][j];
    }

    Matrix(double[][] value) {
        this(value.length, value[0].length, value);
    }

    Matrix(int height, int width, double[] value) {
        if (value.length < height * width || height == 0 || width == 0)
            throw new MatrixException("Error constructor arguments");
        this.width = width;
        this.height = height;
        matr = new double[height][width];
        for (int i = 0; i != height; i++)
            for (int j = 0; j != width; j++)
                matr[i][j] = value[i * width + j];
    }

    public Matrix sum(Matrix A) throws MatrixException {
        //Check if exception
        if (A.width != width || A.height != height)
            throw new MatrixException("Error sum arguments");
        double[][] a = new double[A.height][A.width];
        for (int i = 0; i < A.height; i++) {
            for (int j = 0; j < A.width; j++)
                a[i][j] = A.matr[i][j] + matr[i][j];
        }
        return new Matrix(height, width, a);
    }

    public Matrix mull(Matrix A) throws MatrixException {
        if (A.width != height)
            throw new MatrixException("Error mull arguments");
        double a[][] = new double[height][A.width];
        for (int i = 0; i != height; i++)
            for (int j = 0; j != A.width; j++) {
                a[i][j] = 0;
                for (int r = 0; r != width; r++)
                    a[i][j] += matr[i][r] * A.matr[r][j];
            }
        return new Matrix(height, A.width, a);
    }

    public Matrix transp() {
        double[][] a = new double[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                a[j][i] = matr[i][j];
        }
        return new Matrix(width, height, a);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix Matrix = (Matrix) o;

        if (width != Matrix.width) return false;
        if (height != Matrix.height) return false;
        return Arrays.deepEquals(matr, Matrix.matr);

    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + (matr != null ? Arrays.deepHashCode(matr) : 0);
        return result;
    }

    @Override
    public String toString() {
        String res = new String();
        for (int i = 0; i != height; i++)
            res += Arrays.toString(matr[i]) + '\n';
        return res;
    }
}