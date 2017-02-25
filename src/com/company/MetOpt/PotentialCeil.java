package com.company.MetOpt;


/**
 * Created by Lomdji on 25.02.2017.
 */
public class PotentialCeil {
    public boolean sign;
    public boolean markMin;
    public boolean markBase;
    public double cost;
    public int X;
    public int i;
    public int j;
    public double delataCost;
    public PotentialCeil(double c,int i, int j)
    {
        cost=c;
        X=0;
        this.i=i;
        this.j=j;
        delataCost=0;
        markMin=false;
    }
}
