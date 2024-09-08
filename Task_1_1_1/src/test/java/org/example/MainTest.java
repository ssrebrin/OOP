package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Тестирование HeapSort.
 */
class MainTest {

    /**
     * Тест для проверки правильности работы метода heapSort.
     */
    @Test
    public void testHeapSort() {
        HeapSort heapSort = new HeapSort();
        int[] input = {12, 11, 13, 5, 6, 7};
        int[] expected = {5, 6, 7, 11, 12, 13};

        heapSort.heapsort(input);

        assertArrayEquals(expected, input);
    }
}