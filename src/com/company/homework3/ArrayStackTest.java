package com.company.homework3;

/**
 * Created by Lomdji on 03.12.2016.
 */
import org.apache.commons.collections4.IteratorUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.EmptyStackException;

import static org.junit.Assert.*;
import static com.company.homework3.Utils.randomArray;

@RunWith(BlockJUnit4ClassRunner.class)
public class ArrayStackTest {

    Stack<Integer> stack;

    @Before
    public void beforeEach() {
        stack = new ArrayStack<>();
    }

    @Test(expected = EmptyStackException.class)
    public void testPopEmpty() {
        stack.pop();
    }

    @Test(expected = EmptyStackException.class)
    public void testPopEmptyComplex() {
        stack.push(1);
        stack.push(2);
        stack.pop();
        stack.pop();

        stack.pop();
    }

    @Test(expected = EmptyStackException.class)
    public void testPeekEmpty() {
        stack.peek();
    }

    @Test
    public void testStackSize() {
        int N = 100;

        for (int i = 0; i < N; i++)
            stack.push(i);

        assertEquals(N, stack.getSize());

        for (int i = 0; i < N / 2; i++)
            stack.pop();

        assertEquals(N / 2, stack.getSize());

        for (int i = 0; i < N / 2; i++)
            stack.pop();

        assertEquals(0, stack.getSize());
    }

    @Test
    public void testStackIsEmpty() {
        assertTrue(stack.isEmpty());

        stack.push(1);
        assertFalse(stack.isEmpty());

        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testStackData() {
        Integer[] randomValues = randomArray(100);

        for (int value : randomValues) {
            stack.push(value);
        }

        assertArrayEquals(randomValues, IteratorUtils.toArray(stack.iterator()));
    }

    @Test
    public void testPopElements() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        Integer element = stack.pop();

        assertEquals(element, new Integer(4));
        assertEquals(3, stack.getSize());

        element = stack.pop();

        assertEquals(element, new Integer(3));
        assertEquals(2, stack.getSize());
    }

    @Test
    public void testPeekElements() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);

        Integer element = stack.peek();

        assertEquals(element, new Integer(4));
        assertEquals(4, stack.getSize());

        stack.pop();
        element = stack.peek();

        assertEquals(element, new Integer(3));
        assertEquals(3, stack.getSize());
    }


    @Test
    public void testThatThereAreNoPublicFields() {
        Field[] publicFields = stack.getClass().getFields();
        assertEquals("You should encapsulate your logic",
                0, publicFields.length);
    }
}