package org.example;
import java.util.Arrays;
import java.util.Random;

/**
 * Реализации сортировки кучей.
 */
public class HeapSort {
    private int[] generator(int n){
        int [] c = new int[n];
        for(int i = 0; i < n; i ++){
            c[i] = (int) (Math.random() * n);
        }
        return c;
    }
    /**
     * Бесполезный код для создания jar-файла
     *
     * @param args -
     */
    public static void main(String[] args) {
        HeapSort heapSort = new HeapSort();
        for(int i =1;i<=300;i++){
            int [] input = heapSort.generator(i*10000);
            long startTime = System.nanoTime();

            int[] heapsort = heapSort.heapsort(input);

            long endTime = System.nanoTime();
            long duration = endTime - startTime;

            System.out.println("(" + i*10000 + ", " + (endTime - startTime)/100 + ")");
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