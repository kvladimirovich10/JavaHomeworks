package com.company.homework2;

import java.io.*;

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
        //Error!
        try{
            Matrix E=new Matrix(0,3,d);
        }
        catch (MatrixException e) {
            System.out.println(e);
        }


        Vectors [] V=new Vectors[4];
        V[0]=new Vectors(3,2.1);
        V[1]=new Vectors(d);
        V[2]=new Vectors(A[4]);
        V[3]=V[2].Transp();
        //Error!
        try {
            Vectors E =new Vectors(A[3]);
        }
        catch (MatrixException e) {
            System.out.println(e);
        }

        //output
        for (Matrix I : A)
            System.out.println(I);

        for (Vectors I : V)
            System.out.println(I);


        //2) Operation and output of res. Vectors operation Based on matrix Operations, so I demonstrate only Vectors
        System.out.println(V[0].Sum(V[0]));
        System.out.println(V[0].Sum(V[0]).Transp());
        System.out.println(V[2].Mull(V[3]));
        System.out.println(V[3].Mull(V[2]));
        //error
        try{
            System.out.println(V[0].Sum(V[1]));
            System.out.println(V[0].Mull(V[1]));
        }
        catch (MatrixException e) {
            System.out.println(e);
        }
       try{
            System.out.println(V[0].Mull(V[1]));
       }
        catch (MatrixException e) {
            System.out.println(e);
        }
        //3) Serialization. Output in file.
        try(ObjectOutputStream Obout=new ObjectOutputStream(new FileOutputStream("resources/out.txt"))){
            Obout.writeObject(A);
        }
        //4) Deserialization. Input from file.
        try(ObjectInputStream Inout=new ObjectInputStream(new FileInputStream("resources/out.txt"))){
            Matrix[] X = null;
            X = (Matrix[])Inout.readObject();
            for(Object I:X)
                System.out.println(I);
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
        }
    }
}
