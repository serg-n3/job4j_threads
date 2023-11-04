package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int pref;
        int next;
        do {
            pref = count.get();
            next = pref + 1;

        } while (!count.compareAndSet(pref, next));
    }

    public int get() {
        return count.get();
    }
}