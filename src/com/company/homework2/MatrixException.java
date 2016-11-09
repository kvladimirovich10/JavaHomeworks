package com.company.homework2;


/**
 * Created by lomdji on 28.10.2016.
 */
public class MatrixException extends ArithmeticException {

    public MatrixException() {
        super("UnNamedException");
    }

    public MatrixException(String message) {
        super(message);
    }
}