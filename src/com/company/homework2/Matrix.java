package com.company.homework2;

import java.io.Serializable;

/**
 * Created by техно on 28.10.2016.
 */
public class Matrix implements Serializable {
    Matrix(int Width,int Height, double value){
        this.Width=Width;
        this.Height = Height;
        Matr=new double[Height][Width];
        for(int i=0;i!=Width;i++)
            for(int j=0;j!=Width;j++)
                Matr[i][j]=value;
    }
    Matrix(int Size, double value){
        this(Size, Size, value);
    }
    Matrix(int Width, int Height){
        this(Width, Height, 0);
    }
    Matrix(){
        Width=Height=0;
    }
    //Constructor
    Matrix(int Width, int Height,double[][] value) {
        this.Width=Width;
        this.Height = Height;
        Matr=new double[Height][Width];
        for(int i=0;i!=Width;i++)
            for(int j=0;j!=Width;j++)
                Matr[i][j]=value[i][j];
    }
    Matrix(int Width,int Height,double[] value){
        this.Width=Width;
        this.Height = Height;
        Matr=new double[Height][Width];
        for(int i=0;i!=Width;i++)
            for(int j=0;j!=Width;j++)
                Matr[i][j]=value[i*Width+j];
    }
    public Matrix Summ(Matrix A, Matrix B)throws MatrixException{
         //Check if exception
        if(A.Width!=B.Width || A.Height!=B.Height||A.Width==0)
            throw new MatrixException();
        double[][] a=new double[A.Width][A.Height];
        for (int i = 0; i < A.Height; i++) {
            for(int j = 0; j < A.Width; j++)
                a[i][j]=A.Matr[i][j]+B.Matr[i][j];
        }
        return new Matrix(A.Width,A.Height,a);
    }
    public Matrix Mull(Matrix A, Matrix B)throws MatrixException{
        if(A.Width != B.Height)
            throw new MatrixException();
        double a[][]=new double[A.Height][B.Width];
        for(int i=0;i!=A.Height;i++)
            for(int j = 0; j != B.Width; j++){
                a[i][j]=0;
                for(int r=0;r!=A.Width;r++)
                    a[i][j]+=A.Matr[i][r]*B.Matr[r][j];
            }
        return new Matrix(A.Width,A.Height,a);
    }
    public Matrix Transp(){
        if (Width == 0 || Height == 0)
            return  new Matrix();
        double[][] a=new double[Height][Width];
        for (int i = 0; i < Height; i++) {
            for(int j = 0; j < Width; j++)
                a[j][i]=Matr[i][j];
        }
        return new Matrix(Width,Height,a);
    }
    protected int Width;
    protected int  Height;
    protected double[][] Matr;
}