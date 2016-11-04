package com.company.homework2;

import java.io.IOException;

/**
 * Created by lomdji on 31.10.2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        //1)Creating objects. Output.
        Matrix [] A =new Matrix[5];
        A[0]=new Matrix(3,3,2);
        A[1]=new Matrix(3,1);
        double[][] c={{3,4},{2,1}};
        A[2]=new Matrix(c);
        double[] d={1,2,3,4};
        A[3]=new Matrix(2,2,d);
        A[4]=new Matrix(4,1,d);

        Vectors [] V=new Vectors[4];
        V[0]=new Vectors(4,2.1);
        V[1]=new Vectors(d);
        V[2]=new Vectors(A[4]);
        V[3]=V[2].Transp();

        //output
        for (Matrix I : A)
            System.out.println(I);

        for (Vectors I : V)
            System.out.println(I);




        //2) Operation and output of res.
       // System.out.println(A[0].Mull(A[1]).Transp());
        //3) Serialization. Output in file.

        //4) Deserialization. Input from file.
    }
}
