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
            fin.read(arr);
            for (int i = 1; i < arr.length; i++) {
                arr[i] ^= arr[i - 1];
            }
            res = new String(arr);
        }
        return res;
    }
}