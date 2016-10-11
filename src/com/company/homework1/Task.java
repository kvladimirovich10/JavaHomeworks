package com.company.homework1;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Task {

    public static void main(String[] args) throws IOException {
        // 1) read input and ouput filenames (relative to resources folder e.g. input.txt output.txt)
        String inputFilePath = "resources/input.txt";
        String outputFilePath = "resources/output.txt";

        // 2) Create XorFileEncoder implementing FileEncoder interface
        FileEncoder encoder = new XorFileEncoder();
        encoder.endcode(inputFilePath, outputFilePath);

        // 3) Create XorFileDecoder implementing FileDecoder interface
        FileDecoder decoder = new XorFileDecoder();
        String result;
        try {
            result = decoder.decode(outputFilePath);
            System.out.println(result);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}