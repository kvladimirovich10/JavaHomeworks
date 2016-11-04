package com.company.homework2;

/**
 * Created by ����� on 29.10.2016.
 */
public class Vectors extends Matrix{
    Vectors(){
        super();
        IsDirectionVertical=true;
    }
    Vectors(int Size, double Value) {
        super(Size,1,Value);
    }
    Vectors(int Size, double Value[]) {
        super(Size,1,Value);
        IsDirectionVertical=true;
        for(int i=0;i<Height;i++)
            Matr[1][i] = Value[i];
    }
    Vectors(Matrix Base){
        if(Base.Width!=1 && Base.Height!=1)
            throw new MatrixException();
        else{
            Width=Base.Width; Height=Base.Height;
            Matr=new double[Height][Width];
            for(int i=0;i!=Height;i++)
                for(int j=0;j!=Width;j++)
                    Matr[i][j]=Base.Matr[i][j];
            IsDirectionVertical=Width==1?true:false;
        }
    }
    public Vectors Summ(Vectors A) {
        return new Vectors (new Matrix(A.Height, A.Width, A.Matr).Summ(new Matrix(Height, Width, Matr)));
    }
    public Vectors Mull(Vectors A) {
        return new Vectors (new Matrix(A.Height, A.Width, A.Matr).Mull(new Matrix(Height, Width, Matr)));
    }
    public Vectors Transp() {
        IsDirectionVertical =! IsDirectionVertical;
        return new Vectors(new Matrix(Height,Width,Matr).Transp());
    }
    public double GetComponentByInd(int Ind) {
        return Matr[1][Ind];
    }

    @Override
    public String toString() {
        String Res=new String();
        if(IsDirectionVertical)
            for(int i=0;i!=Height;i++)
                Res+=Matr[i][0]+'\n';
        else
            for(int i=0;i!=Width;i++)
                Res+=Matr[0][i];
        return Res;
    }
    boolean IsDirectionVertical;
}
