package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

public class ArrTest {
    @Test
    void testArrCreation() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1000003);
        list.add(6);
        Arr arr = new Arr(list);

        assertEquals(list, arr.array, "Array should match the input list");
        assertFalse(arr.flag.get(), "Flag should be initially false");
        assertFalse(arr.done.get(), "Done should be initially false");
        assertFalse(arr.busy.get(), "Busy should be initially false");
        assertEquals(0, arr.last, "Last should be initially 0");
    }

    @Test
    void testSetFlags() {
        Arr arr = new Arr(new LinkedList<>());
        arr.flag.set(true);
        arr.done.set(true);
        arr.busy.set(true);
        arr.last = 5;

        assertTrue(arr.flag.get(), "Flag should be set to true");
        assertTrue(arr.done.get(), "Done should be set to true");
        assertTrue(arr.busy.get(), "Busy should be set to true");
        assertEquals(5, arr.last, "Last should be updated to 5");
    }
}