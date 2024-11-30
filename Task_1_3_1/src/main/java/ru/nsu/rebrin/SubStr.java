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
    static Long[] sub_string(String path, String subString) {

        if (subString.isEmpty()) {
            return new Long[0];
        }

        int buffSize = 950;
        List<Long> res = new ArrayList<>();
        Ss table = new Ss();

        byte[] subBytes = subString.getBytes(StandardCharsets.UTF_8);

        try (FileInputStream fis = new FileInputStream(path)) {
            byte[] byteBuffer = new byte[buffSize];
            int bytesRead;
            int i = 0;
            int ind = 0;
            while ((bytesRead = fis.read(byteBuffer)) != -1) {
                for (int j = 0; j < bytesRead; j++) {
                    table.find(res, subBytes, byteBuffer[j], (long) i, (long) ind);
                    i++;
                    if ((byteBuffer[j] & 0b11000000) != 0b10000000) {
                        ind++;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return res.toArray(new Long[0]);
    }

}