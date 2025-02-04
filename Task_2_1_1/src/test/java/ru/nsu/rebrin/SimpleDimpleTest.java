package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleDimpleTest {
    @Test
    void testPrime1() {
        int[] arr = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41};
        assertFalse(SimpleDimple.notAllPrime1(arr));
    }
    @Test
    void testPrime2() {
        int[] arr = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41};
        SimpleDimple sd = new SimpleDimple();
        assertFalse(sd.notAllPrime2(arr, 3));
    }
    @Test
    void testPrime3() {
        int[] arr = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41};
        SimpleDimple sd = new SimpleDimple();
        assertFalse(sd.notAllPrime3(arr));
    }
    @Test
    void testNotPrime1() {
        int[] arr = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 4};
        assertTrue(SimpleDimple.notAllPrime1(arr));
    }
    @Test
    void testNotPrime2() {
        int[] arr = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 4};
        SimpleDimple sd = new SimpleDimple();
        assertTrue(sd.notAllPrime2(arr, 3));
    }
    @Test
    void testNotPrime3() {
        int[] arr = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 4};
        SimpleDimple sd = new SimpleDimple();
        assertTrue(sd.notAllPrime3(arr));
    }
}