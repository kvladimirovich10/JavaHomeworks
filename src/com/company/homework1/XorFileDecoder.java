package com.company.homework1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by техно on 01.10.2016.
 */
public class XorFileDecoder implements FileDecoder {
    public String decode(String inputFilePath) {
        String res;
        try (FileInputStream fin = new FileInputStream(inputFilePath)) {
            byte prev[] = new byte[1];
            byte curr[] = new byte[1];
            if (fin.read(prev) == -1)
                return "Empty file";
            res = (new String(prev));
            while (fin.read(curr) != -1) {
                curr[0] ^= prev[0];
                res += (new String(curr));
                prev[0] = curr[0];
            }
        } catch (FileNotFoundException e) {
            return "Heвoзмoжнo найти файл" + e;
        } catch (IOException e) {
            return " Heвoзмoжнo открыть файл" + e;
        }
        return res;
    }
}