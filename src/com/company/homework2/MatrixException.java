package com.company.homework2;


import sun.plugin2.message.Message;

/**
 * Created by lomdji on 28.10.2016.
 */
public class MatrixException extends ArithmeticException {
   public MatrixException(){
       message="UnNamedException";
   }
    public MatrixException(String message)
    {
        this.message=message;
    }
    public  String toString(){
        return ("MatrixException " + message);
    }
    private String message;
}