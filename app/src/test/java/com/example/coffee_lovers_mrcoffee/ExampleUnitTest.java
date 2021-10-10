package com.example.coffee_lovers_mrcoffee;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void subtract_isCorrect() {
        assertEquals(0, 2 - 2);
    }

    @Test
    public void multiply_isCorrect() {
        assertEquals(10, 5 * 2);
    }

    @Test
    public void divideByZero_isCorrect() {
        assertThrows(Exception.class, () -> System.out.println(10 / 0));
    }
}