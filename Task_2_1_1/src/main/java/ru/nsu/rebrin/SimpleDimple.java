package ru.nsu.rebrin;

import java.lang.Thread;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Non prim number.
 */
public class SimpleDimple {
    /**
     * Generate stats.
     *
     * @param args - args
     */
    public static void main(String[] args) {
        int[] array = generateLargeArray(1000000);
        SimpleDimple sd = new SimpleDimple();

        // Последовательное выполнение
        long startTime = System.nanoTime();
        boolean result1 = sd.notAllPrime1(array);
        long endTime = System.nanoTime();
        System.out.println("" + (endTime - startTime));

        int[] threadCounts = {2, 4, 8, 16};
        for (int thCount : threadCounts) {
            startTime = System.nanoTime();
            boolean result2 = sd.notAllPrime2(array, thCount);
            endTime = System.nanoTime();
            System.out.println("" + (endTime - startTime));
        }

        startTime = System.nanoTime();
        boolean result3 = sd.notAllPrime3(array);
        endTime = System.nanoTime();
        System.out.println("" + (endTime - startTime));
    }

    /**
     * Generate large array.
     *
     * @param size - size
     * @return - array
     */
    public static int[] generateLargeArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(1000000) + 1;
        }
        return array;
    }

    /**
     * Sequential time.
     *
     * @param arr - array
     * @return - bool
     */
    public static boolean notAllPrime1(int[] arr) {

        for (int i : arr) {
            if (!isPr(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * thCount threads.
     *
     * @param arr     - array
     * @param thCount - thread count
     * @return - bool
     */
    public boolean notAllPrime2(int[] arr, int thCount) {
        AtomicBoolean flag = new AtomicBoolean(false);

        int size = (int) Math.ceil((double) arr.length / thCount);
        Thread[] t = new Thread[thCount];
        for (int i = 0; i < thCount; i++) {
            int start = i * size;
            int end = Math.min(start + size, arr.length);
            t[i] = new Thread(new Thread2(arr, start, end, flag));
            t[i].start();
        }

        for (Thread i : t) {
            try {
                i.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        return flag.get();
    }

    /**
     * Parallel.
     *
     * @param arr - array
     * @return - bool
     */
    public boolean notAllPrime3(int[] arr) {
        return Arrays.stream(arr).parallel().anyMatch(num -> !isPr(num));
    }

    /**
     * Is Prime.
     *
     * @param num - number
     * @return - bool
     */
    public static boolean isPr(int num) {
        if (num < 2) {
            return false;
        }

        int sqrt = (int) Math.sqrt(num);
        for (int i = 2; i <= sqrt; i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Thread class.
     */
    class Thread2 implements Runnable {
        private int[] array;
        private int start;
        private int end;
        private AtomicBoolean flag;

        /**
         * Init.
         *
         * @param array - array
         * @param start - start
         * @param end   - end
         * @param flag  - flag which we will change
         */
        public Thread2(int[] array, int start, int end, AtomicBoolean flag) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.flag = flag;
        }

        /**
         * Run.
         */
        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                if (!isPr(array[i])) {
                    flag.set(true);
                }
            }
        }
    }
}