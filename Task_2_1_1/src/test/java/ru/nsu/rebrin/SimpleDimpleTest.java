package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


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


    @Test
    public void testPerformance() {
        SimpleDimple sd = new SimpleDimple();
        int[] array = SimpleDimple.generateLargeArray(1000000);

        long startTime = System.nanoTime();
        boolean result1 = sd.notAllPrime1(array);
        long endTime = System.nanoTime();
        System.out.println("Sequential time: " + (endTime - startTime) + " ns");

        int[] threadCounts = {2, 4, 8, 16};
        for (int thCount : threadCounts) {
            startTime = System.nanoTime();
            boolean result2 = sd.notAllPrime2(array, thCount);
            endTime = System.nanoTime();
            System.out.println("Parallel time with " + thCount + " threads: " + (endTime - startTime) + " ns");
        }

        startTime = System.nanoTime();
        boolean result3 = sd.notAllPrime3(array);
        endTime = System.nanoTime();
        System.out.println("ParallelStream time: " + (endTime - startTime) + " ns");

        assertEquals(result1, result3);
        for (int thCount : threadCounts) {
            assertEquals(result1, sd.notAllPrime2(array, thCount));
        }
    }
}