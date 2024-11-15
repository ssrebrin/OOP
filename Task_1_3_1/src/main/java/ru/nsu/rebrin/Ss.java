package ru.nsu.rebrin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SubString finder.
 */
public class Ss {
    List<List<Integer>> table;

    /**
     * ss.
     */
    Ss() {
        table = new ArrayList<>();
    }

    /**
     * Add to table.
     *
     * @param ind - ind
     * @param num - num
     */
    void add(int ind, int num) {
        table.add(new ArrayList<>(Arrays.asList(ind, num)));
    }

    /**
     * Find.
     *
     * @param res    - res
     * @param subStr - subStr
     * @param letter - letter
     * @param ind    - ind
     */
    void find(List<Integer> res, String subStr, char letter, int ind) {
        if (letter == subStr.charAt(0)) {
            add(ind, 0);
        }

        var iterator = table.iterator(); // Используем итератор для обхода списка

        while (iterator.hasNext()) {
            List<Integer> pair = iterator.next();

            if (subStr.charAt(pair.get(1)) == letter) {
                if (pair.get(1) + 1 == subStr.length()) {
                    res.add(pair.get(0));  // Добавляем начальный индекс найденной подстроки
                    iterator.remove();     // Удаляем элемент через итератор
                } else {
                    pair.set(1, pair.get(1) + 1); // Увеличиваем значение второго элемента
                }
            }
        }
    }

}
