package com.company.MetOpt;

import org.ejml.data.DenseMatrix64F;
import org.ejml.simple.SimpleMatrix;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.LinkedList;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import static com.company.MetOpt.MatrixVectorsOperations.*;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by Lomdji on 06.04.2017.
 */


public class Gradient {
    private double[] x0;
    private double eps;
    private int CallsToFuncGrad = 0;
    private int CallsToFuncDFP = 0;
    private int CallsToFuncNSK = 0;
    private LinkedList<Double> AlphGrad;
    private double m;

    private double M;

    private int IterationsConstStep = 0;
    private int IterationsStepest = 0;
    private int IterationsDFP = 0;
    private int IterationsScaling = 0;

    Gradient(double[] x0, double eps, double m, double M) {
        this.eps = eps;
        this.x0 = x0;
        this.m = m;
        this.M = M;
        AlphGrad=new LinkedList<>();
    }

    private double optimizedFunction(double[] args) {
        return (args[0] * args[0]) + 4 * args[0] + 3 * args[1] + Math.exp(args[0] * args[0] + args[1] * args[1]);
    }

    private double[] gradient(double args[]) {
        double[] res = new double[args.length];
        res[0] = 2 * args[0] + 4 + (2 * args[0]) * Math.exp(args[0] * args[0] + args[1] * args[1]);
        res[1] = 3 + (2 * args[1]) * Math.exp(args[0] * args[0] + args[1] * args[1]);
        return res;
    }

    private double dixotomy(double eps, double[] xk, double[] grad) {
        double alph_st = 0;
        double alph_fin = 1;
        double x1 = alph_st;
        double x2 = alph_fin;
        double delt;
        double[] args_left = new double[xk.length];
        double[] args_right = new double[xk.length];
        while (abs(alph_fin - alph_st) > 2 * eps) {
            CallsToFuncGrad += 2;
            delt = (alph_fin - alph_st) * 0.001;
            x1 = (alph_st + alph_fin) / 2 - delt;
            x2 = (alph_st + alph_fin) / 2 + delt;
            for (int i = 0; i != xk.length; i++) {
                args_left[i] = xk[i] - x1 * grad[i];
                args_right[i] = xk[i] - x2 * grad[i];
            }
            if (optimizedFunction(args_left) > optimizedFunction(args_right))
                alph_st = x1;
            else
                alph_fin = x2;
        }
        return (alph_fin + alph_st) / 2;
    }

    private double norm(double[] x) {
        double res = 0;
        for (double a : x) {
            res += (a * a);
        }
        return Math.sqrt(res);
    }

    public double[] gradientDecaySteepest() {
        double[] res = x0.clone();
        double[] grad = gradient(res);
        double alph;
        double innnerstep = 2 / (M + m);
        while (norm(grad) >= eps) {
            alph = dixotomy(innnerstep, res, grad);
            AlphGrad.add(alph);
            for (int i = 0; i != res.length; i++)
                res[i] -= alph * grad[i];
            grad = gradient(res);
            IterationsStepest++;
        }
        CallsToFuncNSK=CallsToFuncGrad;
        CallsToFuncGrad=0;
        return res;
    }

    public double[] gradientDecayConstStep() {
        double[] res = x0.clone();
        double[] grad = gradient(res);
        double alph = 2 / (M + m);
        //double alph=2*(1-eps)/M;
        while (norm(grad) >= eps) {
            for (int i = 0; i != res.length; i++)
                res[i] -= alph * grad[i];
            grad = gradient(res);
            IterationsConstStep++;
        }
        return res;
    }

    public double[] DFP() {
        double[] res = x0.clone();
        double[] x = x0.clone();
        double[] s = new double[x0.length];
        double[] p = new double[x0.length];
        double[] y = new double[x0.length];
        double[] gradientRes=gradient(res);
        double[] gradientX=gradient(x);
        double[][] B = {{1, 0}, {0, 1}};
        double[] tmp;
        double[][] second;
        double[][] third;
        double alph;
        double innnerstep = 2 / (M + m);
        //innnerstep=0.1;
        while (norm(gradientRes) >= eps) {

            //find xk+1
            gradientX=gradientRes;
            p = mult(B,gradientX);
            alph = dixotomy(innnerstep, x, p);
            /*alph=0.1;*/
            res = summ(x, neg(mult(alph, p)));

            //find Sk
            s = summ(res, neg(x));

            //find Yk
            gradientRes=gradient(res);
            y = summ(gradientRes, neg(gradientX));

            //find Bk+1
            tmp = mult(B, y);
            second = mult(tmp, tmp);
            second = mult((1 / scalarProduct(tmp, y)), second);
            third = mult(1 / scalarProduct(s, y), mult(s, s));

            B = summ(summ(B, neg(second)), third);
            IterationsDFP++;
            x = res;
        }
        CallsToFuncDFP=CallsToFuncGrad;
        CallsToFuncGrad=0;
        return res;
    }

    public double[] ScalingMethod() {
        double[] res = x0.clone();
        double[] x = x0.clone();
        double[] p = new double[x0.length];
        double rot=3*Math.PI/4;
        double[][] G = {{Math.cos(rot), Math.sin(rot)}, {-Math.sin(rot),Math.cos(rot)}};
        double[][] Gt= {{Math.cos(rot), -Math.sin(rot)}, {Math.sin(rot),Math.cos(rot)}};
        double[][] A={{1.5,0},{0,1}};
        double alph;
        double innnerstep = 2 / (M + m);
        double [][]B=mult(mult(G,A),Gt);

        while (norm(gradient(res)) >= eps) {

            //find xk+1
            p = mult(B, gradient(x));
            alph = dixotomy(innnerstep, x, p);
            res = summ(x, neg(mult(alph, p)));

            IterationsScaling++;
            x = res;
        }
        return res;
    }


    public void printArray(double[] x) {
        for (int i = 0; i < x.length; i++) {
            System.out.print(x[i] + "\t");
        }
        System.out.println();
    }

    public void PrintIterrations() {
        System.out.println("Итерации градиент постоянный шаг: " + IterationsConstStep);
        System.out.println("Итерации градиент наискорейший: " + IterationsStepest + "  Дихотоми"+ CallsToFuncNSK);
        System.out.println("Итерации DFP: " + IterationsDFP + "  Дихотоми"+ CallsToFuncDFP);
        System.out.println("Итерации Метода изменения масштабов: " + IterationsScaling);
    }

    public void printSteps(){
        for(int i=0;i!=AlphGrad.size();i++)
            System.out.println("МНС=" + AlphGrad.get(i)+ "     Постоянный шаг="+2/(M+m));
    }

    public static void main(String[] args) {
        double[] x_start = {0, 0};
        double eps = 0.001;
        double m = 2;
        double M = 75;
        Gradient method = new Gradient(x_start, eps, m, M);
        double res[];
        res = method.gradientDecayConstStep();
        method.printArray(res);
        res = method.gradientDecaySteepest();
        method.printArray(res);
        //method.printSteps();


        res = method.DFP();
        method.printArray(res);
        res = method.ScalingMethod();
        method.printArray(res);

        method.PrintIterrations();
    }
}



