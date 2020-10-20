package main.java.zad1;

import java.util.concurrent.atomic.AtomicInteger;

public class MyBinarySemaphore {
    private boolean opened = true;

    public synchronized void acquire() {
        try {
            while (!opened) {
                wait();
            }
        } catch (InterruptedException ignored) {}

        changeStatus(false);
    }

    public synchronized void release() {
        try {
            while (opened) {
                wait();
            }
        } catch (InterruptedException ignored) {}

        changeStatus(true);
    }

    private synchronized void changeStatus(boolean newStatus) {
        opened = newStatus;
        notifyAll();
    }
}
