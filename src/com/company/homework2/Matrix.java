package com.company.homework2;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by lomdji on 28.10.2016.
 */
public class Matrix implements Serializable {
    Matrix(int Height,int Width, double value){
        if(Height==0||Width==0)
            throw new MatrixException("Error constructor arguments");
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
    protected Matrix(){}
    //Constructor
    Matrix(int Height, int Width,double[][] value)throws MatrixException{
        if(Height==0||Width==0 || value.length<Height||value[0].length<Width)
            throw new MatrixException("Error constructor arguments");
        this.Width=Width;
        this.Height = Height;
        Matr=new double[Height][Width];
        for(int i=0;i!=Height;i++)
            for(int j=0;j!=Width;j++)
                Matr[i][j]=value[i][j];
    }
    Matrix(double[][] value) {
        this(value.length,value[0].length,value);
    }
    Matrix(int Height,int Width,double[] value){
        if (value.length<Height*Width||Height==0||Width==0)
            throw new MatrixException("Error constructor arguments");
        this.Width=Width;
        this.Height = Height;
        Matr=new double[Height][Width];
        for(int i=0;i!=Height;i++)
            for(int j=0;j!=Width;j++)
                Matr[i][j]=value[i*Width+j];
    }
    public Matrix Sum(Matrix A)throws MatrixException{
         //Check if exception
        if(A.Width!=Width || A.Height!=Height)
            throw new MatrixException("Error sum arguments");
        double[][] a=new double[A.Height][A.Width];
        for (int i = 0; i < A.Height; i++) {
            for(int j = 0; j < A.Width; j++)
                a[i][j]=A.Matr[i][j]+Matr[i][j];
        }
        return new Matrix(Height,Width,a);
    }
    public Matrix Mull(Matrix A)throws MatrixException{
        if(A.Width != Height)
            throw new MatrixException("Error mull arguments");
        double a[][]=new double[Height][A.Width];
        for(int i=0;i!=Height;i++)
            for(int j = 0; j != A.Width; j++){
                a[i][j]=0;
                for(int r=0;r!=Width;r++)
                    a[i][j]+=Matr[i][r]*A.Matr[r][j];
            }
        return new Matrix(Height,A.Width,a);
    }
    public Matrix Transp(){
        double[][] a=new double[Width][Height];
        for (int i = 0; i < Height; i++) {
            for(int j = 0; j < Width; j++)
                a[j][i]=Matr[i][j];
        }
        return new Matrix(Width,Height,a);
    }

    @Override
    public String toString() {
        String Res =new String();
        for(int i=0;i!=Height;i++)
            Res+=Arrays.toString(Matr[i])+'\n';
        return Res;
    }

    protected int Width;
    protected int  Height;
    protected double[][] Matr;
}