package com.company.MetOpt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.function.UnaryOperator;

import static java.lang.Math.abs;
import static java.lang.Math.cos;

/**
 * Created by Lomdji on 16.03.2017.
 */
public class UncondOpt {
    private double a = 0.3/*0*/;
    private double b = 1;
    private double eps = 0.0001;

    private double functionCounter = 0;

    private double delt;

    UncondOpt(double e, double a, double b, UnaryOperator<Double> Transformer) {
        eps = e;
        optimizedFunction = Transformer;
        this.a = a;
        this.b = b;
    }

    private int demensionOnIteratin = 10;

    UnaryOperator<Double> optimizedFunction;

    private int uniformIterations = 0;

    private int dixotomyIterations = 0;

    public int getDemensionOnIteratin() {
        return demensionOnIteratin;
    }

    public int getDixotomyIterations() {
        return dixotomyIterations;
    }

    /*private double optimizedFunction(double x) {
        functionCounter++;
        //return 0.5*x*x-sin(x);
        return x * x - 2 * x - 2 * cos(x);
    }*/

    public double Derivativa(double x) {
        return x - cos(x);
    }

    public int getUniformIterations() {
        return uniformIterations;
    }

    public double uniformStep() {
        double x_st = a;
        double x_fin = b;
        double res = Double.POSITIVE_INFINITY;
        double[] x = new double[demensionOnIteratin + 1];
        double[] f = new double[demensionOnIteratin + 1];
        double step;
        while (abs(x_st - x_fin) > 2 * eps) {
            step = (x_fin - x_st) / demensionOnIteratin;
            for (int i = 0; i != x.length; i++) {
                x[i] = x_st + i * step;
                f[i] = optimizedFunction.apply(x[i]);
            }
            int i_min = 0;
            double min = Double.POSITIVE_INFINITY;
            for (int i = 0; i != f.length; i++) {
                if (f[i] < min) {
                    i_min = i;
                    min = f[i];
                }
            }
            if (i_min == x.length - 1)
                i_min = x.length - 2;
            else if (i_min == 0)
                i_min = 1;
            x_st = x[i_min - 1];
            x_fin = x[i_min + 1];
            res = x[i_min];
            uniformIterations++;
        }
        functionCounter = 0;
        return res;
    }

    public double dixotomy() {
        double x_st = a;
        double x_fin = b;
        double x1;
        double x2;
        delt = 0.0000001;
        while (abs(x_fin - x_st) > 2 * eps) {
            //delt=(x_fin-x_st)*0.001;
            x1 = (x_st + x_fin) / 2 - delt;
            x2 = (x_st + x_fin) / 2 + delt;
            if (optimizedFunction.apply(x_st) > optimizedFunction.apply(x_fin))
                x_st = x1;
            else
                x_fin = x2;
            dixotomyIterations++;
        }
        return (x_fin + x_st) / 2;
    }

    public static void main(String[] args) {
        UncondOpt solver = new UncondOpt(0.0001, 0.3,1, (Double o1) -> {
            return o1 * o1 - 2 * o1 - 2 * cos(o1);
        });
        System.out.format("Results Uniform: X_min = %f ; DemensiononOnItterstuin= %d ; Itterrations = %d \n",
                solver.uniformStep(), solver.getDemensionOnIteratin(), solver.getUniformIterations());
        System.out.format("Results Dixotomy: X_min = %f ; Itterrations = %d \n",
                solver.dixotomy(), solver.getDixotomyIterations());
        System.out.format("Result derivative= %f \n", solver.Derivativa(solver.dixotomy()));
    }
}