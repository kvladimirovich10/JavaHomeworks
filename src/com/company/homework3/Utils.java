package com.company.homework3;

/**
 * Created by Lomdji on 03.12.2016.
 */

import java.util.Random;

public class Utils {
    public static Integer[] randomArray(int size) {
        Random random = new Random();

        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt() % (2 * size);
        }
        return array;
    }
}
