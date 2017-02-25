package com.company.MetOpt;
import java.io.*;
import java.math.*;
import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * Created by Lomdji on 25.02.2017.
 */
public class Potentials {
    //Demensions of transport task;
    private int n;
    private int m;
    private int[] A;
    private int[] B;
    private double[] U;
    private double[] V;
    private PotentialCeil[][] Ceils;

    public Potentials(String inputCFilePath, String inputAFilePath, String inputBFilePath, int n, int m) {
        Ceils = new PotentialCeil[n][m];
        A = new int[n];
        B = new int[m];
        U = new double[n];
        V = new double[n];
        this.n = n;
        this.m = m;
        try (Scanner inpA = new Scanner(new FileInputStream(inputAFilePath));
             Scanner inpB = new Scanner(new FileInputStream(inputBFilePath));
             Scanner inpC = new Scanner(new FileInputStream(inputCFilePath))) {
            for (int i = 0; i != n; i++) {
                for (int j = 0; j != m; j++) {
                    Ceils[i][j] = new PotentialCeil(inpC.nextDouble(),i,j);
                    if (i == 0) {
                        B[j] = inpB.nextInt();
                        V[j] = Double.NaN;
                    }
                }
                A[i] = inpA.nextInt();
                U[i]=Double.NaN;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Heвoзмoжнo найти файл" + e);
        } catch (IOException e) {
            System.out.println("Heвoзмoжнo открыть файл" + e);
        }
    }
    public double[][] solve(){
        //check closing
        if(!checkClosing())
            return null;
        makeBasedPlanMinimalElement();
        if(checkDegrodation())
            System.out.println("Degrodation!!!");
        countPotentials();
        while (!checkOptimal()) {
            redistribution();
            countPotentials();
        }
        return null;
    }

    private void countPotentials(){
        boolean firstflag=true;
        for(int i=0;i!=Ceils.length;i++){
            for(int j=0;j!=Ceils[i].length;j++){
                if(Ceils[i][j].markBase==true) {
                    if(firstflag)
                        U[i]=0;
                }
                else{
                    if(U[i]==Double.NaN)
                        U[i]=Ceils[i][j].cost-V[j];
                    else
                        V[j]=Ceils[i][j].cost-U[i];
                }
            }

        }
    }

    private boolean checkOptimal(){
        boolean res=true;
        for(int i=0;i!=Ceils.length;i++){
            for(int j=0;j!=Ceils[i].length;j++){
                if(Ceils[i][j].markBase==false){
                    Ceils[i][j].delataCost=Ceils[i][j].cost-(U[i]+V[j]);
                    if(Ceils[i][j].delataCost<0)
                        res=false;
                }
            }
        }
        return res;
    }

    private void redistribution(){
        PotentialCeil Tmp=findMaximal();

    }

    private PotentialCeil findMaximal(){
        PotentialCeil res=null;
        double rese=0;
        for(int i=0;i!=Ceils.length;i++) {
            for (int j = 0; j != Ceils[i].length; j++) {
                if (Ceils[i][j].markBase == false && abs(Ceils[i][j].delataCost) > rese) {
                    res = Ceils[i][j];
                    rese = abs(Ceils[i][j].delataCost);
                }
            }
        }
        return res;
    }

    private PotentialCeil findMinimal(){
        PotentialCeil res=Ceils[0][0];
        for(int i=0;i!=Ceils.length;i++) {
            for (int j = 0; j != Ceils[i].length; j++) {
                if (Ceils[i][j].cost<res.cost && Ceils[i][j].markMin==false)
                    res=Ceils[i][j];
            }
        }
        return res;
    }

    private boolean checkDegrodation(){
        int counter=0;
        for(int i=0;i!=Ceils.length;i++){
            for(int j=0;j!=Ceils[i].length;j++) {
                Ceils[i][j].markMin = false;
                if (Ceils[i][j].X != 0) {
                    counter++;
                }
            }
        }
        return counter!=(m+n-1);
    }

    private static boolean IsNull(int array[]) {
        for(int i=0;i!=array.length;i++)
            if(array[i]!=0)
                return false;
        return true;
    }

    public void makeBasedPlanMinimalElement() {
        int[] Reserves = B.clone();
        int[] Needs = A.clone();
        while (!IsNull(Reserves) || !IsNull(Needs)) {
            PotentialCeil Tmp = findMinimal();
            if (Reserves[Tmp.i] < Needs[Tmp.j]) {
                Tmp.X = Reserves[Tmp.i];
                Tmp.markBase=true;
                for (int j = 0; j != Ceils[Tmp.i].length; j++)
                    Ceils[Tmp.i][j].markMin = true;
                Needs[Tmp.j]-=Reserves[Tmp.i];
                Reserves[Tmp.i]=0;
            }
            else if(Reserves[Tmp.i] > Needs[Tmp.j]){
                Tmp.X = Needs[Tmp.j];
                Tmp.markBase=true;
                for (int i = 0; i != Ceils.length; i++)
                    Ceils[i][Tmp.j].markMin = true;
                Reserves[Tmp.i]-=Needs[Tmp.j];
                Needs[Tmp.j]=0;
            }
            else{
                Tmp.X = Needs[Tmp.j];
                Tmp.markBase=true;
                for (int i = 0; i != Ceils.length; i++)
                    Ceils[i][Tmp.j].markMin = true;
                for (int j = 0; j != Ceils[Tmp.i].length; j++)
                    Ceils[Tmp.i][j].markMin = true;
                Needs[Tmp.j]=0;
                Reserves[Tmp.i]=0;
                //repair degrodation
                if(!IsNull(Reserves)||!IsNull(Needs)) {
                    boolean flag=false;
                    for(int j=0;j!=Needs.length && !flag;j++){
                        if(Needs[j]!=0) {
                            for(int i=0;i!=Ceils.length && !flag;i++ ){
                                if(Ceils[i][j].markBase==false && Ceils[i][j].markMin==false && Ceils[i][j].X==0) {
                                    Ceils[i][j].markBase = true;
                                    flag=true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public boolean checkClosing() {
        int sum1=0;
        int sum2=0;
        for (int i = 0; i!=A.length;i++)
            sum1+=A[i];
        for (int i = 0; i!=B.length;i++)
            sum2+=B[i];
        return sum1==sum2;
    }
    public static void main(String[] args){
        String inputCFilePath="";
        String inputAFilePath="";
        String inputBFilePath="";
        int[] demension={4,5};
        Potentials Task=new Potentials(inputCFilePath,inputAFilePath,inputBFilePath,demension[0],demension[1]);
        Task.solve();
    }

}
