package ru.nsu.rebrin;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ssTest {

    @Test
    void test(){
        ss t = new ss();
        List<Integer> res = new ArrayList<>();
        t.find(res,"ab",'a', 0);
        t.find(res,"ab",'b', 1);
        assertEquals(res, List.of(0));
    }
    @Test
    void test1(){
        ss t = new ss();
        List<Integer> res = new ArrayList<>();
        t.find(res,"aaa",'a', 0);
        t.find(res,"aaa",'a', 1);
        t.find(res,"aaa",'a', 2);
        t.find(res,"aaa",'a', 3);
        t.find(res,"aaa",'b', 4);
        assertEquals(res, List.of(0,1));
    }
}