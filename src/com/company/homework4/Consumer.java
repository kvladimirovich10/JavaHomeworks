package com.company.homework4;

import java.util.Random;

/**
 * Created by Lomdji on 29.01.2017.
 */

import static com.company.homework4.MatrixGenerationUtils.*;

class Consumer extends Thread {
    private double values[];
    private Conveyor conveyor;
    Random random;

    public Consumer(Conveyor conveyor) {
        this.conveyor = conveyor;
        random = new Random();
    }

    private void getNewArray(double[] array) {
        values = array;
    }

    @Override
    public void run() {
        while (conveyor.hasNext()) {
            getNewArray(conveyor.next());
            for (int i = 0; i < values.length; i++) {
                values[i] = randomValue(random);
            }
        }
    }
}
