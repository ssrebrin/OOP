package ru.nsu.rebrin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Substring finder.
 */
public class Ss {
    List<List<Long>> table;

    /**
     * Constructor for Ss.
     */
    Ss() {
        table = new ArrayList<>();
    }

    /**
     * Adds an element to the table.
     *
     * @param ind   the index
     * @param num   the number
     * @param index ind
     */
    void add(Long ind, Long num, Long index) {
        table.add(new ArrayList<>(Arrays.asList(ind, num, index)));
    }

    /**
     * Finds the substring by bytes.
     *
     * @param res    res
     * @param subStr substr
     * @param letter letter
     * @param ind    ind
     * @param index  index of utf
     */
    void find(List<Long> res, byte[] subStr, byte letter, Long ind, Long index) {
        if (letter == subStr[0]) {
            add(ind, (long) 0, index);
        }

        var iterator = table.iterator();

        while (iterator.hasNext()) {
            List<Long> pair = iterator.next();

            if (subStr[pair.get(1).intValue()] == letter) {
                if (pair.get(1) + 1 == subStr.length) {
                    res.add(pair.get(2));
                    iterator.remove();
                } else {
                    pair.set(1, pair.get(1) + 1);
                }
            } else {
                iterator.remove();
            }
        }
    }

}