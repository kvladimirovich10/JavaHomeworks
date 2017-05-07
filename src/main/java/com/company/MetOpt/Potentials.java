package com.company.MetOpt;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * Created by Lomdji on 25.02.2017.
 */
public class Potentials {
    //Demensions of transport task;
    private int n;   /*число покупателей*/
    private int m;  /*число поставщиков*/
    private int[] A; /*запасы поставщика*/
    private int[] B;  /*запросы покупателя*/
    private double[] U;
    private double[] V;
    private boolean isDegrodation=false;
    private PotentialCeil[][] Ceils;
    enum DIR{
        RIGHT,DOWN,UP,LEFT};

    public Potentials(String inputCFilePath, String inputAFilePath, String inputBFilePath, int n, int m) throws FileNotFoundException, IOException {
        Ceils = new PotentialCeil[m][n];
        A = new int[m];
        B = new int[n];
        U = new double[m];
        V = new double[n];
        this.n = n;
        this.m = m;
        try (Scanner inpA = new Scanner(new FileInputStream(inputAFilePath));
             Scanner inpB = new Scanner(new FileInputStream(inputBFilePath));
             Scanner inpC = new Scanner(new FileInputStream(inputCFilePath))) {
            for (int i = 0; i != m; i++) {
                for (int j = 0; j != n; j++) {
                    Ceils[i][j] = new PotentialCeil(inpC.nextDouble(),i,j);
                    if (i == 0) {
                        B[j] = inpB.nextInt();
                        V[j] = Double.POSITIVE_INFINITY;
                    }
                }
                A[i] = inpA.nextInt();
                U[i]=Double.POSITIVE_INFINITY;
            }
        }
    }

    private void solve() {
        //check closing
        if (!checkClosing())
            return;
        makeBasedPlanMinimalElement();
        System.out.println("Опорный план!");
        if (checkDegrodation()) {
            RepairDegrodationCasebyRandomValue();
            System.out.println("Degrodation!!!");
        }
        while(checkRepairdDegrodation())
            RepairDegrodationCasebyRandomValue();
        if(checkRepairdDegrodation())
            System.out.println("Degrodation not repaired!!!");
        SystemOutX();
        countPotentials();
         while (!checkOptimal()) {
            redistribution();
            if(checkDegrodation()) {
                while(checkRepairdDegrodation()) {
                    RepairDegrodationCasebyRandomValue();
                }
            }
            SystemOutX();
            countPotentials();
        }
    }
    boolean checkNotLoop(){
        LinkedList<PotentialCeil> Loop=new LinkedList<PotentialCeil>();
        for(int i=0;i!=Ceils.length;i++) {
            for (int j = 0; j != Ceils[i].length; j++) {
                if (Ceils[i][j].markBase == true) {
                    Loop.addLast(Ceils[i][j]);
                    if(makeLoop(Ceils[i][j],Loop,Ceils[i][j],null)) {
                        return true;
                    }
                }
            }
        }
        return true;
    }

    private void RepairDegrodationCasebyRandomValue() {
        Random random=new Random();
        int i= random.nextInt(Ceils.length);  /*0*/
        int j= random.nextInt(Ceils.length);  /*1*/
        boolean end=true;
        while(end) {
            i= random.nextInt(Ceils.length);  /*0*/
            j= random.nextInt(Ceils.length);  /*1*/
            while (Ceils[i][j].markBase != false) {
                i = random.nextInt(Ceils.length);    /*0*/
                j = random.nextInt(Ceils[0].length);  /*1*/
            }
            Ceils[i][j].markBase = true;
            Ceils[i][j].X = 0;
            LinkedList<PotentialCeil> Loop=new LinkedList<PotentialCeil>();
            Loop.addLast(Ceils[i][j]);
            if (!makeLoop(Ceils[i][j],Loop,Ceils[i][j],null))
                return;
            else {
                Ceils[i][j].markBase = false;
            }
        }
    }

    private void CountPotentialsGorizont(int i){
        for(int j=0;j!=Ceils[i].length;j++) {
            if(Ceils[i][j].markBase && (V[j]==Double.POSITIVE_INFINITY)){
                V[j]=Ceils[i][j].cost-U[i];
                CountPotentialsVertical(j);
            }
        }
    }
    private void CountPotentialsVertical(int j){
        for(int i=0;i!=Ceils.length;i++) {
            if(Ceils[i][j].markBase && (U[i]==Double.POSITIVE_INFINITY)){
                U[i]=Ceils[i][j].cost-V[j];
                CountPotentialsGorizont(i);
            }
        }
    }
    private void countPotentials(){
        boolean firstflag=true;
        //To null
        for(int i=0;i!=U.length;i++)
            U[i]=Double.POSITIVE_INFINITY;
        for(int j=0;j!=V.length;j++)
            V[j]=Double.POSITIVE_INFINITY;
        U[0]=0;
        CountPotentialsGorizont(0);
    }


    private boolean LoopHasNull(LinkedList<PotentialCeil> Loop){
        for (PotentialCeil t:Loop) {
            if(t.X==0)
                return true;
        }
        return false;
    }

    private boolean checkOptimal(){
        boolean res=true;
        for(int i=0;i!=Ceils.length;i++){
            for(int j=0;j!=Ceils[i].length;j++){
                if(!Ceils[i][j].markBase){
                    Ceils[i][j].delataCost=Ceils[i][j].cost-(U[i]+V[j]);
                    if(Ceils[i][j].delataCost<0)
                        res=false;
                }
            }
        }
        return res;
    }

    boolean makeLoop(PotentialCeil current_base,LinkedList<PotentialCeil> Loop,PotentialCeil Start, DIR from){
        if(Loop.size()!=1 && Start==current_base){
            Loop.removeLast();
            return true;
        }
        for(DIR dir : DIR.values()) {
            if(dir==DIR.RIGHT && dir!=from){
                for(int j=current_base.j+1;j!=Ceils[0].length;j++){
                    if(Ceils[current_base.i][j].markBase==true){
                        Loop.addLast(Ceils[current_base.i][j]);
                        if(from!=DIR.LEFT && from!=null)
                            current_base.changedDirection=true;
                        if(makeLoop(Ceils[current_base.i][j],Loop,Start,DIR.LEFT))
                            return true;
                        current_base.changedDirection=false;
                        break;
                    }
                }
            }
            else if(dir==DIR.UP && dir!=from){
                for(int i=current_base.i-1;i>=0;i--){
                    if(Ceils[i][current_base.j].markBase==true){
                        Loop.addLast(Ceils[i][current_base.j]);
                        if(from!=DIR.DOWN && from!=null)
                            current_base.changedDirection=true;
                        if(makeLoop(Ceils[i][current_base.j],Loop,Start,DIR.DOWN))
                            return true;
                        current_base.changedDirection=false;
                        break;
                    }
                }
            }
            else if(dir==DIR.DOWN && dir!=from){
                for(int i=current_base.i+1;i!=Ceils.length;i++){
                    if(Ceils[i][current_base.j].markBase==true){
                        Loop.addLast(Ceils[i][current_base.j]);
                        if(from!=DIR.UP && from!=null)
                            current_base.changedDirection=true;
                        if(makeLoop(Ceils[i][current_base.j],Loop,Start,DIR.UP))
                            return true;
                        current_base.changedDirection=false;
                        break;
                    }
                }
            }
            else if(dir==DIR.LEFT && dir!=from) {
                for (int j = current_base.j - 1; j >= 0; j--) {
                    if (Ceils[current_base.i][j].markBase == true) {
                        Loop.addLast(Ceils[current_base.i][j]);
                        if (from != DIR.RIGHT && from != null)
                            current_base.changedDirection = true;
                        if (makeLoop(Ceils[current_base.i][j], Loop, Start, DIR.RIGHT))
                            return true;
                        current_base.changedDirection=false;
                        break;
                    }
                }
            }
        }
        PotentialCeil t= Loop.removeLast();
        t.changedDirection=false;
        return false;
     }

    private void fillDirections(LinkedList<PotentialCeil>Loop){
        Loop.getFirst().changedDirection=true;
        PotentialCeil curr;
        for(int k=1;k!=Loop.size()-1;k++){
             curr=Loop.get(k);
             if(curr.i == Loop.get(k+1).i && curr.i == Loop.get(k-1).i)
                 curr.changedDirection=false;
             else if(curr.j == Loop.get(k+1).j && curr.j == Loop.get(k-1).j)
                 curr.changedDirection=false;
             else
                 curr.changedDirection=true;
        }
        curr=Loop.getLast();
        if(curr.i == Loop.getFirst().i && curr.i == Loop.get(Loop.size()-2).i)
            curr.changedDirection=false;
        else if(curr.j == Loop.getFirst().j && curr.j == Loop.get(Loop.size()-2).j)
            curr.changedDirection=false;
        else
            curr.changedDirection=true;
     }

    private void redistribution(){
        PotentialCeil Tmp=findMaximal();
        Tmp.sign=1;
        LinkedList<PotentialCeil> Loop=new LinkedList<PotentialCeil>();
        Loop.addLast(Tmp);
        Tmp.markBase=true;
        Tmp.changedDirection=true;
        //построение цикла
        makeLoop(Tmp,Loop,Tmp,null);
        int i=1;
        fillDirections(Loop);
        for (PotentialCeil t:Loop ) {
            if(t.changedDirection==true) {
                t.sign = i;
                i *= (-1);
            }
        }
        int minimal=Integer.MAX_VALUE;
        for (PotentialCeil t:Loop) {
            if(t.sign==-1 && t.X<minimal)
                minimal=t.X;
        }
        for (PotentialCeil t:Loop) {
            if(t.changedDirection) {
                t.X += (t.sign * minimal);
                t.changedDirection=false;
            }
            if (t.X == 0 && t!=Loop.getFirst())
                t.markBase = false;
        }
    }

    private PotentialCeil findMaximal(){
        PotentialCeil res=null;
        double rese=0;
        for(int i=0;i!=Ceils.length;i++) {
            for (int j = 0; j != Ceils[i].length; j++) {
                if (!Ceils[i][j].markBase && Ceils[i][j].delataCost<0 && abs(Ceils[i][j].delataCost) > rese) {
                    res = Ceils[i][j];
                    rese = abs(Ceils[i][j].delataCost);
                }
            }
        }
        return res;
    }

    private PotentialCeil findMinimal(){
        PotentialCeil res=null;
        double rese=Double.POSITIVE_INFINITY;
        for(int i=0;i!=Ceils.length;i++) {
            for (int j = 0; j != Ceils[i].length; j++) {
                if ((Ceils[i][j].cost < rese) && !Ceils[i][j].markMin) {
                    res = Ceils[i][j];
                    rese=Ceils[i][j].cost;
                }
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
        if(counter!=(m+n-1))
            isDegrodation=true;
        else
            isDegrodation=false;
        return isDegrodation;
    }

    private boolean checkRepairdDegrodation(){
        int counter=0;
        for(int i=0;i!=Ceils.length;i++){
            for(int j=0;j!=Ceils[i].length;j++) {
                Ceils[i][j].markMin = false;
                if (Ceils[i][j].markBase== true) {
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

    //Проверить в случаи ошибки
    private void makeBasedPlanMinimalElement() {
        int[] Reserves = A.clone();
        int[] Needs = B.clone();
        while (!IsNull(Reserves) || !IsNull(Needs)) {
            PotentialCeil Tmp = findMinimal();
            if (Reserves[Tmp.i] < Needs[Tmp.j]) {
                Tmp.X = Reserves[Tmp.i];
                Tmp.markBase=true;
                Tmp.markMin=true;
                for (int j = 0; j != Ceils[Tmp.i].length; j++)
                    Ceils[Tmp.i][j].markMin = true;
                Needs[Tmp.j]-=Reserves[Tmp.i];
                Reserves[Tmp.i]=0;
            }
            else if(Reserves[Tmp.i] > Needs[Tmp.j]){
                Tmp.X = Needs[Tmp.j];
                Tmp.markBase=true;
                Tmp.markMin=true;
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
                /*if(!IsNull(Reserves)||!IsNull(Needs)) {
                    boolean flag=false;
                    for(int j=0;j!=Needs.length && !flag;j++){
                        if(Needs[j]!=0) {
                            for(int i=0;i!=Ceils.length && !flag;i++ ){
                                if(!Ceils[i][j].markBase && Ceils[i][j].markMin && Reserves[i]==0) {
                                    Ceils[i][j].markBase = true;
                                    Ceils[i][j].markMin=true;
                                    flag=true;
                                }
                            }
                        }
                    }
                }*/
            }
        }
    }

    private boolean checkClosing() {
        int sum1=0;
        int sum2=0;
        for (int i = 0; i!=A.length;i++)
            sum1+=A[i];
        for (int i = 0; i!=B.length;i++)
            sum2+=B[i];
        return sum1==sum2;
    }

    public void SystemOutX() {
        double cost=0;
        for (int i = 0; i != Ceils.length; i++){
            for (int j = 0; j != Ceils[i].length; j++) {
                if (Ceils[i][j].markBase == true) {
                    System.out.print("b"+Ceils[i][j].X+"    ");
                    cost+=(Ceils[i][j].cost*Ceils[i][j].X);
                }
                else
                    System.out.print("0    ");
            }
            System.out.println();
        }
        System.out.println("Итоговая стоимость составляет: "+cost);
    }

    public static void main(String[] args){
        String inputCFilePath="resources/Costs.txt";
        String inputAFilePath="resources/Supplier.txt";
        String inputBFilePath="resources/Consumer.txt";
        int[] demension={5,4};
        Potentials Task;
        try {
            Task = new Potentials(inputCFilePath,inputAFilePath,inputBFilePath,demension[0],demension[1]);
            Task.solve();
            Task.SystemOutX();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Program finished");
    }
}
