package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class SsTest {

    @Test
    void test() {
        Ss t = new Ss();
        List<Long> res = new ArrayList<>();
        t.find(res, "ab".getBytes(), (byte) 'a', (long) 0, (long) 0);
        t.find(res, "ab".getBytes(), (byte) 'b', (long) 1, (long) 1);
        assertEquals(res, List.of((long) 0));
    }

    @Test
    void test1() {
        Ss t = new Ss();
        List<Long> res = new ArrayList<>();
        t.find(res, "aaa".getBytes(), (byte) 'a', (long) 0, (long) 0);
        t.find(res, "aaa".getBytes(), (byte) 'a', (long) 1, (long) 1);
        t.find(res, "aaa".getBytes(), (byte) 'a', (long) 2, (long) 2);
        t.find(res, "aaa".getBytes(), (byte) 'a', (long) 3, (long) 3);
        t.find(res, "aaa".getBytes(), (byte) 'b', (long) 4, (long) 4);
        assertEquals(res, List.of((long) 0, (long) 1));
    }
}
