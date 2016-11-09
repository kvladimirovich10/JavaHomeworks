package com.company.homework2;

import java.io.*;

/**
 * Created by lomdji on 31.10.2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        //1)Creating objects. Output.
        Matrix[] A = new Matrix[5];
        A[0] = new Matrix(3, 3, 2);
        A[1] = new Matrix(3, 1);
        double[][] c = {{3, 4}, {2, 1}};
        A[2] = new Matrix(c);
        double[] d = {1, 2, 3, 4};
        A[3] = new Matrix(2, 2, d);
        A[4] = new Matrix(4, 1, d);
        //Error!
        try {
            Matrix E = new Matrix(0, 3, d);
        } catch (MatrixException e) {
            e.printStackTrace();
        }


        Vector[] V = new Vector[4];
        V[0] = new Vector(3, 2.1);
        V[1] = new Vector(d);
        V[2] = new Vector(A[4]);
        V[3] = V[2].transp();
        //Error!
        try {
            Vector E = new Vector(A[3]);
        } catch (MatrixException e) {
            e.printStackTrace();
        }

        //output
        for (Matrix I : A)
            System.out.println(I);

        for (Vector I : V)
            System.out.println(I);


        //2) Operation and output of res. Vectors operation Based on matrix Operations, so I demonstrate only Vectors
        System.out.println(V[0].sum(V[0]));
        System.out.println(V[0].sum(V[0]).transp());
        System.out.println(V[2].mull(V[3]));
        System.out.println(V[3].mull(V[2]));
        System.out.println(V[3].dot(V[2]));
        //error
        try {
            System.out.println(V[0].sum(V[1]));
        } catch (MatrixException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(V[0].mull(V[1]));
        } catch (MatrixException e) {
            e.printStackTrace();
        }
        //3) Serialization. Output in file.
        try (ObjectOutputStream Obout = new ObjectOutputStream(new FileOutputStream("resources/out.txt"))) {
            Obout.writeObject(A);
        }
        //4) Deserialization. Input from file.
        try (ObjectInputStream Inout = new ObjectInputStream(new FileInputStream("resources/out.txt"))) {
            Matrix[] X;
            X = (Matrix[]) Inout.readObject();
            boolean isEquals = true;
            for (int i = 0; i != X.length; i++)
                if (!X[i].equals(A[i]))
                    isEquals = false;
            System.out.println("Are Matrix Equals after Serialization:  "+isEquals);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
