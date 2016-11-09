package com.company.homework2;

/**
 * Created by Lomdji on 29.10.2016.
 */
public class Vector extends Matrix {
    boolean isDirectionVertical = true;

    Vector(int size, double value) {
        super(size, 1, value);
    }

    Vector(double value[]) {
        super(value.length, 1, value);
    }

    Vector(Matrix base) {
        if (base.width != 1 && base.height != 1)
            throw new MatrixException("Error in Vector Constructor: Bots sizes of baseMatrix not 1 ");
        else {
            width = base.width;
            height = base.height;
            matr = new double[height][width];
            for (int i = 0; i != height; i++)
                for (int j = 0; j != width; j++)
                    matr[i][j] = base.matr[i][j];
            isDirectionVertical = width == 1 ? true : false;
        }
    }

    public Vector sum(Vector A) {
        return new Vector(super.sum(A));
    }

    //Scalar
    public double dot(Vector A) throws MatrixException {
        int size1 = (isDirectionVertical ? height : width);
        int size2 = (A.isDirectionVertical ? A.height : A.width);
        if (size1 != size2)
            throw new MatrixException("Not equal vectors sizes");
        double res = 0;
        for (int i = 0; i != size1; i++)
            res += this.getComponentByInd(i) * A.getComponentByInd(i);
        return res;
    }

    public Vector transp() {
        return new Vector(super.transp());
    }

    public double getComponentByInd(int ind) {
        if (isDirectionVertical)
            return matr[ind][0];
        else
            return matr[0][ind];
    }

}
