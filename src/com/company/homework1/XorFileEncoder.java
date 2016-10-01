package com.company.homework1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Lomdjy on 01.10.2016.
 */
public class XorFileEncoder implements FileEncoder {
    public void endcode(String inputFilePath, String outputFilePath) {
        try (FileInputStream fin = new FileInputStream(inputFilePath);FileOutputStream fout=new FileOutputStream(outputFilePath)){
            byte prev[]=new byte[1];
            byte curr[]=new byte[1];
            byte tmp;
            if(fin.read(prev)==-1)
                return;
            fout.write(prev);
            while(fin.read(curr)!=-1) {
                tmp=curr[0];
                curr[0]^=prev[0];
                fout.write(curr);
                prev[0]=tmp;
            }
        }catch(FileNotFoundException e) {
            System.out.println(" Heвoзмoжнo найти файл" + e);
            return;
        }catch (IOException e) {
            System.out.println(" Heвoзмoжнo открыть файл" + e);
        }
    }
}