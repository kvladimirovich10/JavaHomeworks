package com.company.homework4;
import java.util.Random;
/**
 * Created by Vaio on 29.01.2017.
*/
public class Consumer extends Thread {
    private double values[];
    private Conveyor conveyor;

    public Consumer(Conveyor conveyor) {
        this.conveyor=conveyor;
    }
    public synchronized void getNewArray(double[] array) {
        values=array;
    }
    public void run()
    {
        while(conveyor.iterates()) {
            getNewArray(conveyor.currnet());
            for (double i : values) {
                Random random = new Random();
                i = random.nextDouble();
            }
        }
    }
}
