package com.company.homework3;

/**
 * Created by Lomdji on 03.12.2016.
 */
import java.util.Iterator;

public class ArrayStack<T> implements Stack<T> {
    private T[] Array;

    public ArrayStack() {
    }
    // Constructor by Base
    public ArrayStack(T[] Base) {
        Array=Base.clone();
    }

    @Override
    public T push(T item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T pop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T peek() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getSize() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

}