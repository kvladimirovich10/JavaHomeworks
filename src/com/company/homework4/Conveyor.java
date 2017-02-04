package com.company.homework4;

/**
 * Created by Lomdji on 30.01.2017.
 */
class Conveyor {
    private volatile int currentstring;
    private double[][] array;

    Conveyor(double[][] array) {
        this.array = array;
        currentstring = -1;
    }

    public synchronized boolean hasNext() {
        return currentstring < array.length - 1;
    }
    public synchronized double[] next(){
        currentstring++;
        return array[currentstring];
    }
}
