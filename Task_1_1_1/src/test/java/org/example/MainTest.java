package org.example;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Тестирование HeapSort.
 */
class MainTest {

    @Test
    public void testHeapSort() {
        HeapSort heapSort = new HeapSort();
        int[] input = {12, 11, 13, 5, 6, 7};
        int[] expected = {5, 6, 7, 11, 12, 13};

        heapSort.heapsort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSort1() {
        HeapSort heapSort = new HeapSort();
        int[] input = {3, -1, -1, 2, 0, 5, -3};
        int[] expected = {-3, -1, -1, 0, 2, 3, 5};

        heapSort.heapsort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSort2() {
        HeapSort heapSort = new HeapSort();
        int[] input = {3, 3, 3, 3};
        int[] expected = {3, 3, 3, 3};

        heapSort.heapsort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSort3() {
        HeapSort heapSort = new HeapSort();
        int[] input = {};
        int[] expected = {};

        heapSort.heapsort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSort4() {
        HeapSort heapSort = new HeapSort();
        int[] input = {0};
        int[] expected = {0};

        heapSort.heapsort(input);

        assertArrayEquals(expected, input);
    }

    @Test
    public void testHeapSort5() {
        HeapSort heapSort = new HeapSort();
        int[] input = {4, 2, 1, 0, -1, -4};
        int[] expected = {-4, -1, 0, 1, 2, 4};

        heapSort.heapsort(input);

        assertArrayEquals(expected, input);
    }
}