package com.company.homework3;

/**
 * Created by Lomdji on 03.12.2016.
 */

import java.util.EmptyStackException;
import java.util.Iterator;

import static java.lang.System.arraycopy;

public class ArrayStack<T> implements Stack<T> {
    private Object[] array;
    private int size = 0;

    public ArrayStack() {
        array = new Object[2];
    }

    @Override
    public T push(T item) {
        if (size == array.length)
            resize_stack(size * 2);
        array[size++] = item;
        return item;
    }

    @Override
    public T pop() {
        if (size != 0) {
            T tmp = (T) array[--size];
            array[size] = null;
            if (size < array.length / 4)
                resize_stack(array.length / 2 + 1);
            return tmp;
        } else
            throw new EmptyStackException();
    }

    @Override
    public T peek() {
        if (size != 0)
            return (T) array[(size - 1)];
        else
            throw new EmptyStackException();
    }

    @Override
    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    @Override
    public long getSize() {
        return (long) size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int current = 0;
            public boolean hasNext() {
                return (current < size);
            }
            @Override
            public T next() {
                return (T) array[current++];
            }
        };
    }

    private void resize_stack(int nsize) {
        Object[] tmp = new Object[nsize];
        arraycopy(array, 0, tmp, 0, size);
        array = tmp;
    }
}