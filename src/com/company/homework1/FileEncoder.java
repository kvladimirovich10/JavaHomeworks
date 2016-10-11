package com.company.homework1;


public interface FileEncoder {
    /**
     * Encode inputFile writing the result to outputFile
     *
     * @param inputFilePath  - input file path (relative to resources folder)
     * @param outputFilePath - output file path (relative to resources folder)
     */
    void endcode(String inputFilePath, String outputFilePath);

    default void encode(String inputFilePath) {
        endcode(inputFilePath, "default_output");
    }
}
