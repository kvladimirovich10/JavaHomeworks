package com.company.homework2;

import java.io.Serializable;

/**
 * Created by техно on 28.10.2016.
 */
public class Matrix implements Serializable {
    Matrix(int Height,int Width, double value){
        this.Width=Width;
        this.Height = Height;
        Matr=new double[Height][Width];
        for(int i=0;i!=Height;i++)
            for(int j=0;j!=Width;j++)
                Matr[i][j]=value;
    }
    Matrix(int Size, double value){
        this(Size, Size, value);
    }
    Matrix(int Height, int Width){
        this(Height, Width, 0);
    }
    Matrix(){
        Width=Height=0;
    }
    //Constructor
    Matrix(int Height, int Width,double[][] value) {
        this.Width=Width;
        this.Height = Height;
        Matr=new double[Height][Width];
        for(int i=0;i!=Height;i++)
            for(int j=0;j!=Width;j++)
                Matr[i][j]=value[i][j];
    }
    Matrix(int Height,int Width,double[] value){
        this.Width=Width;
        this.Height = Height;
        Matr=new double[Height][Width];
        for(int i=0;i!=Height;i++)
            for(int j=0;j!=Width;j++)
                Matr[i][j]=value[i*Width+j];
    }
    public Matrix Summ(Matrix A)throws MatrixException{
         //Check if exception
        if(A.Width!=Width || A.Height!=Height||A.Width==0)
            throw new MatrixException();
        double[][] a=new double[A.Width][A.Height];
        for (int i = 0; i < A.Height; i++) {
            for(int j = 0; j < A.Width; j++)
                a[i][j]=A.Matr[i][j]+Matr[i][j];
        }
        return new Matrix(Width,Height,a);
    }
    public Matrix Mull(Matrix A)throws MatrixException{
        if(A.Width != Height)
            throw new MatrixException();
        double a[][]=new double[A.Height][Width];
        for(int i=0;i!=A.Height;i++)
            for(int j = 0; j != Width; j++){
                a[i][j]=0;
                for(int r=0;r!=A.Width;r++)
                    a[i][j]+=A.Matr[i][r]*Matr[r][j];
            }
        return new Matrix(Width,Height,a);
    }
    public Matrix Transp(){
        if (Width == 0 || Height == 0)
            return  new Matrix();
        double[][] a=new double[Width][Height];
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