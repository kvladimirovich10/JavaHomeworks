package com.company.homework4;

import java.util.Random;

/**
 * Created by Lomdji on 29.01.2017.
 */
public class Consumer extends Thread {
    private double values[];
    private Conveyor conveyor;
    Random random;

    public Consumer(Conveyor conveyor) {
        this.conveyor = conveyor;
        random = new Random();
        start();
    }

    public void getNewArray(double[] array) {
        values = array;
    }

    public void run() {
        while (conveyor.iterates()) {
            getNewArray(conveyor.current());
            for (int i = 0; i < values.length; i++) {
                values[i] = random.nextDouble();
            }
        }
    }
}
