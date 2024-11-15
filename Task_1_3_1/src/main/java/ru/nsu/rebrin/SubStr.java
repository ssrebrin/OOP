package ru.nsu.rebrin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SubStr {

    /**
     * Find substring
     *
     * @param path - path
     * @param subString - ss
     * @return - array of index
     */
    static int[] sub_string(String path, String subString) {

        if (subString.isEmpty()){
            return new int[0];
        }

        int buff_size = 1000;
        char[] buff = new char[1000];
        List<Integer> res = new ArrayList<>();
        Ss table = new Ss();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int i = 0;

            br.read(buff, 0, buff_size);

            for (char a : buff) {
                table.find(res, subString, a, i);
                i++;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return res.stream().mapToInt(Integer::intValue).toArray();
    }
}
