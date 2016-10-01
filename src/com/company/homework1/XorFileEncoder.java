package com.company.homework1;

import java.io.*;

/**
 * Created by Lomdjy on 01.10.2016.
 */
public class XorFileEncoder implements FileEncoder {
    public void endcode(String inputFilePath, String outputFilePath) {
        try (BufferedInputStream fin = new BufferedInputStream(new FileInputStream(inputFilePath));
             BufferedOutputStream fout=new BufferedOutputStream(new FileOutputStream(outputFilePath))){
            int prev, curr;
            int tmp;
            if((prev=fin.read())==-1)
                return;
            fout.write(prev);
            while((curr=fin.read())!=-1){
                tmp=curr;
                curr^=prev;
                fout.write(curr);
                prev=tmp;
            }
        }catch(FileNotFoundException e) {
            System.out.println(" Heвoзмoжнo найти файл" + e);
            return;
        }catch (IOException e) {
            System.out.println(" Heвoзмoжнo открыть файл" + e);
        }
    }
}