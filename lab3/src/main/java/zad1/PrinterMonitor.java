package main.java.zad1;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {

    private final SortedSet<Integer> printersSet = new TreeSet<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition available = lock.newCondition();

    public PrinterMonitor(int printers) {

        for (int i = 0; i < printers; i++) {
            printersSet.add(i);
        }
    }

    public int acquirePrinter() {
        int printerNo = -1;
        lock.lock();
        try {
            while (printersSet.isEmpty()) {
                available.await();
            }
            printerNo = printersSet.first();
            printersSet.remove(printerNo);
        } catch (InterruptedException ignored) {}
        finally {
            lock.unlock();
        }

        return printerNo;
    }

    public void releasePrinter(int printerNo) {
        lock.lock();
        printersSet.add(printerNo);
        available.signalAll();
        lock.unlock();
    }

}
