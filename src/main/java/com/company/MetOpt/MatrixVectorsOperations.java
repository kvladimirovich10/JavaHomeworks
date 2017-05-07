package com.company.MetOpt;

/**
 * Created by Lomdji on 04.05.2017.
 */
public class MatrixVectorsOperations {

    public static double scalarProduct(double[] v1, double[] v2) {
        double res = 0;
        for (int i = 0; i != v1.length; i++)
            res += v1[i] * v2[i];
        return res;
    }

    public static double[] mult(double v1[], double[][] v2) {
        double res[] = new double[v2[0].length];
        for (int i = 0; i < v1.length; i++) {
            res[i] = 0;
            for (int j = 0; j != res.length; j++) {
                res[i] += v1[i] * v2[j][i];
            }
        }
        return res;
    }

    public static double[] mult(double[][] A, double[] b) {
        double[] res = new double[b.length];
        double tmp;
        for (int i = 0; i < b.length; i++) {
            res[i] = 0;
            for (int j = 0; j != A[i].length; j++)
                res[i] += A[i][j] * b[j];
        }
        return res;
    }

    public static double[][] mult(double[] v1, double[] v2) {
        double res[][] = new double[v1.length][v2.length];
        for (int i = 0; i < v1.length; i++) {
            for (int j = 0; j < v2.length; j++) {
                res[i][j] = v1[i] * v2[j];
            }
        }
        return res;
    }

    public static double[][] mult(double[][] v1, double[][] v2) {
        double res[][] = new double[v1.length][v2[0].length];
        for (int i = 0; i < v1.length; i++) {
            for (int j = 0; j < v2.length; j++) {
                res[i][j] = 0;
                for (int k = 0; k < v2.length; k++) {
                    res[i][j] += v1[i][k] * v2[k][j];
                }
            }
        }
        return res;
    }

    public static double[] mult(double a, double[] v1) {
        double[] res = new double[v1.length];
        for (int i = 0; i != v1.length; i++)
            res[i] = v1[i] * a;
        return res;
    }

    public static double[][] mult(double a, double[][] v) {
        double[][] res = new double[v.length][v[0].length];
        for (int i = 0; i != v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                res[i][j] = v[i][j] * a;
            }
        }
        return res;
    }

    public static double[] summ(double[] v1, double[] v2) {
        double[] res = new double[v1.length];
        for (int i = 0; i != v1.length; i++)
            res[i] = v1[i] + v2[i];
        return res;
    }

    public static double[][] summ(double[][] v1, double[][] v2) {
        double[][] res = new double[v1.length][v1[0].length];
        for (int i = 0; i != v1.length; i++) {
            for (int j = 0; j < v1[i].length; j++) {
                res[i][j] = v1[i][j] + v2[i][j];
            }
        }
        return res;
    }

    public static double[] neg(double[] x) {
        double[] res = new double[x.length];
        for (int i = 0; i != x.length; i++)
            res[i] = -x[i];
        return res;
    }

    public static double[][] neg(double[][] x) {
        double[][] res = new double[x.length][x[0].length];
        for (int i = 0; i != x.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                res[i][j] = -x[i][j];
            }
        }
        return res;
    }
}
