package ru.nsu.rebrin;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SubStr.
 */
public class SubStr {

    /**
     * Find substring.
     *
     * @param path      path
     * @param subString substr
     * @return arr of inds
     */
    static int[] sub_string(String path, String subString) {

        if (subString.isEmpty()) {
            return new int[0];
        }

        int buffSize = 950;
        List<Integer> res = new ArrayList<>();
        Ss table = new Ss();

        byte[] subBytes = subString.getBytes(StandardCharsets.UTF_8);

        //System.out.println(Arrays.toString(subBytes));

        try (FileInputStream fis = new FileInputStream(path)) {
            byte[] byteBuffer = new byte[buffSize];
            int bytesRead;
            int i = 0;

            while ((bytesRead = fis.read(byteBuffer)) != -1) {
                for (int j = 0; j < bytesRead; j++) {
                    table.find(res, subBytes, byteBuffer[j], i);
                    i++;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return res.stream().mapToInt(Integer::intValue).toArray();
    }
}