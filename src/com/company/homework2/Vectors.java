package com.company.homework2;

/**
 * Created by Lomdji on 29.10.2016.
 */
public class Vectors extends Matrix{
    Vectors(int Size, double Value) {
        super(Size,1,Value);
        IsDirectionVertical=true;
    }
    Vectors(double Value[]) {
        super(Value.length,1,Value);
        IsDirectionVertical=true;
    }
    Vectors(Matrix Base){
        if(Base.Width!=1 && Base.Height!=1)
            throw new MatrixException("Error in Vector Constructor: Bots sizes of BaseMatrix not 1 ");
        else{
            Width=Base.Width; Height=Base.Height;
            Matr=new double[Height][Width];
            for(int i=0;i!=Height;i++)
                for(int j=0;j!=Width;j++)
                    Matr[i][j]=Base.Matr[i][j];
            IsDirectionVertical=Width==1?true:false;
        }
    }
    public Vectors Sum(Vectors A) {
        return new Vectors (new Matrix(A.Height, A.Width, A.Matr).Sum(new Matrix(Height, Width, Matr)));
    }
    public Matrix Mull(Vectors A) {
        return new Matrix(A.Height, A.Width, A.Matr).Mull(new Matrix(Height, Width, Matr));
    }
    public Vectors Transp() {
        return new Vectors(new Matrix(Height,Width,Matr).Transp());
    }
    public double GetComponentByInd(int Ind) {
        if(IsDirectionVertical)
            return Matr[Ind][0];
        else
            return Matr[0][Ind];
    }
    boolean IsDirectionVertical;
}
