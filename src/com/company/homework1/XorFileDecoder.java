package com.company.homework1;

import java.io.*;


/**
 * Created by техно on 01.10.2016.
 */
public class XorFileDecoder implements FileDecoder {
    public String decode(String inputFilePath) throws IOException {
        String res;
        File file_fin = new File(inputFilePath);
        try (BufferedInputStream fin = new BufferedInputStream(new FileInputStream(inputFilePath))) {
            byte arr[] = new byte[((int) file_fin.length()) + 1];
            int iterator = 0;
            if (fin.read(arr, iterator++, 1) == -1)
                return "Empty file";
            while (fin.read(arr, iterator, 1) != -1) {
                arr[iterator] ^= arr[iterator - 1];
                iterator++;
            }
            res = new String(arr);
        }
        return res;
    }
}