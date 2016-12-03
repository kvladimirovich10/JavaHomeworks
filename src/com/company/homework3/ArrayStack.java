package com.company.homework3;

/**
 * Created by Lomdji on 03.12.2016.
 */
import java.util.Iterator;

import static java.lang.System.arraycopy;

public class ArrayStack<T> implements Stack<T> {
    private Object[] array;
    private int size=0;

    public ArrayStack(int capacity) {
        array=new Object[capacity];
    }

    // Constructor by Base
    public ArrayStack(T[] Base) {
        array=Base.clone();
        size=Base.length;
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

    private void enlarge(int new_size) {
        Object[] tmp=new Object[new_size];
        arraycopy(array,0,tmp,0,array.length);
        array=tmp;
    }

}