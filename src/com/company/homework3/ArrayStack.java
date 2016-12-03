package com.company.homework3;

/**
 * Created by Lomdji on 03.12.2016.
 */

import java.util.EmptyStackException;
import java.util.Iterator;

import static java.lang.System.arraycopy;

public class ArrayStack<T> implements Stack<T> {
    private Object[] array;
    private long size = 0;

    public ArrayStack() {
        array = new Object[2];
    }

    @Override
    public T push(T item) {
        if (size == array.length) {
            Object[] tmp = new Object[(int) size * 2];
            arraycopy(array, 0, tmp, 0, (int) size);
            array = tmp;
        }
        array[(int) size++] = item;
        return item;
    }

    @Override
    public T pop() {
        if (size != 0)
            return (T) array[(int) --size];
        else
            throw new EmptyStackException();

    }

    @Override
    public T peek() {
        if (size != 0)
            return (T) array[(int) (size - 1)];
        else
            throw new EmptyStackException();
    }

    @Override
    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            long current = 0;

            public boolean hasNext() {
                return (current < size);
            }

            @Override
            public T next() {
                return (T) array[(int) current++];
            }
        };
    }
}