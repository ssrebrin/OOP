package ru.nsu.rebrin;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Arr {
    LinkedList<Integer> array;
    AtomicBoolean flag;
    AtomicBoolean done;
    AtomicBoolean busy;
    Integer last = 0;
    public Arr(LinkedList<Integer> aarray) {
        array = aarray;
        flag = new AtomicBoolean(false);
        done = new AtomicBoolean(false);
        busy = new AtomicBoolean(false);
    }
}
