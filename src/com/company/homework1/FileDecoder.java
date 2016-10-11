package com.company.homework1;

import java.io.IOException;

public interface FileDecoder {
    /**
     * Decode inputFile
     *
     * @param inputFilePath - input file path (relative to resources folder)
     * @return decoded file string
     */
    String decode(String inputFilePath) throws IOException;
}
