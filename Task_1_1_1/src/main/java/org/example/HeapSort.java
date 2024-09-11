package org.example;

/**
 * Реализации сортировки кучей.
 */
public class HeapSort {

    /**
     * Бесполезный код для создания jar-файла
     *
     * @param args -
     */
    public static void main(String[] args) {
        int[] array = {12, 11, 13, 5, 6, 7};
        HeapSort sorter = new HeapSort();
        sorter.heapsort(array);

        for (int value : array) {
            System.out.print(value + " ");
        }
    }

    /**
     * Преобразование поддерева в кучу.
     *
     * @param arr - массив
     * @param n - размер кучи
     * @param i - корень поддерева
     */
    private void heapify(int[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }

        if (largest != i) {
            int s = arr[i];
            arr[i] = arr[largest];
            arr[largest] = s;
            heapify(arr, n, largest);
        }
    }

    /**
     * Сортировка массива.
     *
     * @param arr - массив для сортировки
     * @return отсортированный массив
     */
    public int[] heapsort(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }

        return arr;
    }
}