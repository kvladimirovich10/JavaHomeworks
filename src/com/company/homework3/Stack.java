package com.company.homework3;

/**
 * Created by Lomdji on 03.12.2016.
 */
import java.util.EmptyStackException;

public interface Stack<T> extends Iterable<T> {
    /**
     * Pushes an item onto the top of this stack.
     *
     * @param item the item to be pushed onto this stack.
     * @return the item argument.
     */
    T push(T item);

    /**
     * Removes the object at the top of this stack and returns that object as the value of this function.
     *
     * @return the object at the top of this stack
     * @throws EmptyStackException if this stack is empty.
     */
    T pop();

    /**
     * Looks at the object at the top of this stack without removing it from the stack.
     *
     * @return the object at the top of this stack
     * @throws EmptyStackException if this stack is empty.
     */
    T peek();

    /**
     * Tests if this stack is empty
     */
    boolean isEmpty();

    /**
     * @return size of the stack (amount of elements in the stack)
     */
    long getSize();
}