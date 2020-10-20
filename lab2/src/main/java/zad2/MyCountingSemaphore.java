package main.java.zad2;

import java.util.concurrent.atomic.AtomicInteger;

public class MyCountingSemaphore{

    private int counter = 0;
    private final int MAX_COUNTER_VALUE;

    public MyCountingSemaphore(int counter) {
        this.counter = counter;
        MAX_COUNTER_VALUE = counter;
    }

    public synchronized void acquire() {
        try {
            while (counter <= 0) {
                wait();
            }
        } catch (InterruptedException ignored) {}

        counter--;
        notifyAll();
    }

    public synchronized void release() {
        try {
            while (counter >= MAX_COUNTER_VALUE) {
                wait();
            }
        } catch (InterruptedException ignored) {}

        counter++;
        notifyAll();
    }
}
