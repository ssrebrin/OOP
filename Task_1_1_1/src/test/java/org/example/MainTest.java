package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MainTest {
    @Test
    public void testHeapSort() {
        HeapSort heapSort = new HeapSort();
        int[] input = {12, 11, 13, 5, 6, 7};
        int[] expected = {5, 6, 7, 11, 12, 13};

        heapSort.heapsort(input);

        assertArrayEquals(expected, input);
    }
}