package com.company.homework2;


/**
 * Created by lomdji on 28.10.2016.
 */
public class MatrixException extends ArithmeticException {
    private String message;

    public MatrixException() {
        message = "UnNamedException";
    }

    public MatrixException(String message) {
        this.message = message;
    }

    public String toString() {
        return ("MatrixException " + message);
    }
}