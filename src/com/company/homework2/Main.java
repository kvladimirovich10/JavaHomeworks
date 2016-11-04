package com.company.homework2;

import java.io.IOException;

/**
 * Created by техно on 31.10.2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        //1)Creating objects. Output.
        Matrix A=new Matrix(3,3,1);
        double[][] b={{1,2},{3,4}};
        double[][] c={{3,4},{1,2}};
        Matrix B=new Matrix(2,2,b);
        Matrix C=new Matrix(2,2,c);
        System.out.println(A);
        System.out.println(B.Mull(C).Transp());
        //2) Operation and output of res.
        System.out.println(B.Mull(C).Transp());
        //3) Serialization. Output in file.

        //4) Deserialization. Input from file.
    }
}
