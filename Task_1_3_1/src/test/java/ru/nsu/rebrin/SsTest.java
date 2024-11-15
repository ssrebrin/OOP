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
        t.find(res, "ab", 'a', 0);
        t.find(res, "ab", 'b', 1);
        assertEquals(res, List.of(0));
    }

    @Test
    void test1() {
        Ss t = new Ss();
        List<Integer> res = new ArrayList<>();
        t.find(res, "aaa", 'a', 0);
        t.find(res, "aaa", 'a', 1);
        t.find(res, "aaa", 'a', 2);
        t.find(res, "aaa", 'a', 3);
        t.find(res, "aaa", 'b', 4);
        assertEquals(res, List.of(0, 1));
    }
}