package com.company.homework4;
import java.util.Random;
/**
 * Created by Vaio on 29.01.2017.
 */
public class Consumer extends Thread {
    private double values[];

    public Consumer() {
    }
    public Consumer getNewArray(double[] array) {
        values=array;
    }
    public void run()
    {
        for (double i:values) {
            Random random = new Random();
            i=random.nextDouble();
        }
    }
}
