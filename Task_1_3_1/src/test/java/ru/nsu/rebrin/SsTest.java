package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class SsTest {

    @Test
    void test() {
        Ss t = new Ss();
        List<Integer> res = new ArrayList<>();
        t.find(res, "ab".getBytes(), (byte) 'a', 0, 0);
        t.find(res, "ab".getBytes(), (byte) 'b', 1, 1);
        assertEquals(res, List.of(0));
    }

    @Test
    void test1() {
        Ss t = new Ss();
        List<Integer> res = new ArrayList<>();
        t.find(res, "aaa".getBytes(), (byte) 'a', 0, 0);
        t.find(res, "aaa".getBytes(), (byte) 'a', 1, 1);
        t.find(res, "aaa".getBytes(), (byte) 'a', 2, 2);
        t.find(res, "aaa".getBytes(), (byte) 'a', 3, 3);
        t.find(res, "aaa".getBytes(), (byte) 'b', 4, 4);
        assertEquals(res, List.of(0, 1));
    }
}
