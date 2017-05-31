package com.company.MetOpt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@FunctionalInterface
interface Function<Res, Inp1, Inp2, Inp3> {
    Res count(Inp1 from1, Inp2 from2, Inp3 from3);
}

public class ClipHyperplanes {
    private static Function<Double, Double, Double, Double> aim = (x, y, z) -> z;

    public ClipHyperplanes(double eps) {
        this.eps=eps;
    }

    private double norm(Double[] x, Double[] z) {
        double res = 0;
        for (int i = 0; i < x.length; i++) {
            res += Math.pow(x[i] - z[i], 2);
        }
        return Math.sqrt(res);
    }

    private double eps;

    private static ArrayList<Function<Double, Double, Double, Double>> limits = new ArrayList<>(3);
    private static ArrayList<Function<Double[], Double, Double, Double>> limitsGradients = new ArrayList<>(3);

    static {
        limits.add((x, y, z) -> Math.pow(x, 2) - 9);
        limits.add((x, y, z) -> Math.pow(y, 2) - 4);
        limits.add((x, y, z) -> Math.pow(y - 1, 2) + Math.pow(x - 2, 2) - z);

        limitsGradients.add((x, y, z) -> new Double[]{2 * x, 0.0, 0.0});
        limitsGradients.add((x, y, z) -> new Double[]{0.0, 2 * y, 0.0});
        limitsGradients.add((x, y, z) -> new Double[]{2 * (x - 2), 2 * (y - 1), -1.0});
    }

    private boolean checkConditions(double x, double y, double z, List<Function<Double, Double, Double, Double>> lim) {
        for (Function<Double, Double, Double, Double> f : lim) {
            if (f.count(x, y, z) > 0)
                return false;
        }
        return true;
    }

    private static Double[] Convetor(double[] inp) {
        Double[] res = new Double[inp.length - 1];
        for (int i = 0; i < inp.length - 1; i++) {
            res[i] = inp[i];
        }
        return res;
    }

    private double[] findA(Double[] x, ArrayList<Function<Double[], Double, Double, Double>> lim,
                           ArrayList<Function<Double, Double, Double, Double>> limFunc) {
        Double res[];
        int iMax = 0;
        double ValMax = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < lim.size(); i++) {
            if (limFunc.get(i).count(x[0], x[1], x[2]) > ValMax) {
                iMax = i;
                ValMax = limFunc.get(i).count(x[0], x[1], x[2]);
            }
        }
        res = lim.get(iMax).count(x[0], x[1], x[2]);
        double res2[] = new double[lim.size()];
        for (int j = 0; j < res2.length; j++) {
            res2[j] = res[j];
        }
        return res2;
    }

    private double[] vectorFunctionfromB(ArrayList<Double> b, int strsize) {
        double[] res = new double[b.size() + strsize];
        for (int i = 0; i < b.size(); i++) {
            res[i] = -b.get(i);
        }
        for (int i = b.size(); i < res.length; i++) {
            res[i] = 0;
        }
        return res;
    }

    private double[] vectorFunctionfromBAdditional(ArrayList<Double> b, int strsize) {
        double[] res = new double[b.size() + 2 * strsize];
        for (int i = 0; i < b.size(); i++) {
            res[i] = -b.get(i);
        }
        for (int i = b.size(); i < b.size() + strsize; i++) {
            res[i] = 0;
        }
        for (int i = b.size() + strsize; i < res.length; i++) {
            res[i] = -1;
        }
        return res;
    }

    private double[][] ConstrainMatrix(ArrayList<Double[]> a) {
        double res[][] = new double[a.get(0).length][a.size()];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = a.get(j)[i];
            }
        }
        return res;
    }

    private double[] GenArra(int size, double a) {
        double[] res = new double[size];
        Arrays.fill(res, a);
        return res;
    }

    private double[] generateCVect(int size) {
        double[] res = new double[size];
        res[0] = res[1] = 0;
        res[1] = -1;
        for (int i = 2; i < res.length; i++) {
            res[i] = 0;
        }
        return res;
    }


    public Double[] solve() {
        Double xPrev[] = {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
        Double xCurr[] = new Double[3];
        ArrayList<Function<Double, Double, Double, Double>> S = new ArrayList<>(3);
        S.add((x, y, z) -> x - 3);
        S.add((x, y, z) -> -x - 3);
        S.add((x, y, z) -> y - 2);
        S.add((x, y, z) -> -y - 2);
        S.add((x, y, z) -> z - 100);
        S.add((x, y, z) -> -z);
        ArrayList<Double> bt = new ArrayList<>();
        ArrayList<Double[]> at = new ArrayList<>();
        bt.add(3.0);
        bt.add(3.0);
        bt.add(2.0);
        bt.add(2.0);
        bt.add(100.0);
        bt.add(0.0);
        at.add(new Double[]{1.0, 0.0, 0.0});
        at.add(new Double[]{-1.0, 0.0, 0.0});
        at.add(new Double[]{0.0, 1.0, 0.0});
        at.add(new Double[]{0.0, -1.0, 0.0});
        at.add(new Double[]{0.0, 0.0, 1.0});
        at.add(new Double[]{0.0, 0.0, -1.0});
        //Find FirstMin
        Simplex_method solver = new Simplex_method(vectorFunctionfromBAdditional(bt, at.get(0).length), vectorFunctionfromB(bt, at.get(0).length),
                ConstrainMatrix(at), GenArra(at.get(0).length, 0), GenArra(at.get(0).length, 1), generateCVect(at.get(0).length));
        xCurr = Convetor(solver.RunSimpll());
        /*Первая проверка*/
        if (checkConditions(xCurr[0], xCurr[1], xCurr[2], limits))
            return xCurr;
        while (norm(xPrev, xCurr) >= eps) {
            double a[]=findA(xCurr,limitsGradients,limits);
            double tmp = 0;
            for (int i = 0; i != xCurr.length; i++) {
                tmp += a[i] * xCurr[i];
            }
            double b = -aim.count(xCurr[0], xCurr[1], xCurr[2]) + tmp;
            bt.add(b);
            S.add((x, y, z) -> a[0] * x + a[1] * y + a[2] * z - b);
            xPrev = xCurr.clone();
            solver = new Simplex_method(vectorFunctionfromBAdditional(bt, at.get(0).length), vectorFunctionfromB(bt, at.get(0).length),
                    ConstrainMatrix(at), GenArra(at.get(0).length, 0), GenArra(at.get(0).length, 1), generateCVect(at.get(0).length));
            xCurr = Convetor(solver.RunSimpll());
        }
        return xCurr;
    }

    public static void main(String[] args) {
        ClipHyperplanes meth = new ClipHyperplanes(0.001);
        meth.solve().toString();
    }
}
