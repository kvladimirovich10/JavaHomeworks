package com.company.homework2;

/**
 * Created by Lomdji on 29.10.2016.
 */
public class Vector extends Matrix {
    boolean IsDirectionVertical = true;

    Vector(int Size, double Value) {
        super(Size, 1, Value);
    }

    Vector(double Value[]) {
        super(Value.length, 1, Value);
    }

    Vector(Matrix Base) {
        if (Base.Width != 1 && Base.Height != 1)
            throw new MatrixException("Error in Vector Constructor: Bots sizes of BaseMatrix not 1 ");
        else {
            Width = Base.Width;
            Height = Base.Height;
            Matr = new double[Height][Width];
            for (int i = 0; i != Height; i++)
                for (int j = 0; j != Width; j++)
                    Matr[i][j] = Base.Matr[i][j];
            IsDirectionVertical = Width == 1 ? true : false;
        }
    }

    public Vector Sum(Vector A) {
        return new Vector(super.Sum(A));
    }

    //—кал€рное произведение
    /*public double dot(Vector A) {
        int size=Math.max(Math.max(Height,Width),Math.max(A.Height,A.Width))
        for(int i=0;i!=
    }*/

    public Vector Transp() {
        return new Vector(super.Transp());
    }

    public double GetComponentByInd(int Ind) {
        if (IsDirectionVertical)
            return Matr[Ind][0];
        else
            return Matr[0][Ind];
    }

}
