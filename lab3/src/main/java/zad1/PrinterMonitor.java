package main.java.zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {

    private final ReentrantLock lock = new ReentrantLock();
    private int[] printers;

    public PrinterMonitor(int printers) {
        this.printers = new int[printers];

        for (int i = 0; i < printers; i++) {
            this.printers[printers] = i;
        }
    }

    public int acquirePrinter() {
        // TODO: like producer and consumer
    }

}
