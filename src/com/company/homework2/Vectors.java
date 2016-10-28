package com.company.homework2;

/**
 * Created by техно on 29.10.2016.
 */
public class Vectors extends Matrix{
    Vectors() {
        super();
        IsDirectionVertical=true;
    }
    Vectors(int Size, double Value) {
        super(Size,1,Value);
    }
    Vectors(int Size, double Value[]) {
        super(Size,1);
        IsDirectionVertical=true;
        for(int i=0;i<Height;i++)
            Matr[1][i] = Value[i];
    }
    double GetComponentByInd(int Ind) {
        return Matr[1][Ind];
    }
    boolean IsDirectionVertical;
}
