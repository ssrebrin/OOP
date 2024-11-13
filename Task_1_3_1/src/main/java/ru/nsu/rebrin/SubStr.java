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
    static int[] SubString(String path, String subString) {

        if (subString.isEmpty()){
            return new int[0];
        }

        int BUFFER_SIZE = 1000;
        char[] buff = new char[1000];
        List<Integer> res = new ArrayList<>();
        ss table = new ss();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int i = 0;

            br.read(buff, 0, BUFFER_SIZE);

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
