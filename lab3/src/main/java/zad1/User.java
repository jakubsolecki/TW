package main.java.zad1;

import java.util.Random;

import static java.lang.Thread.sleep;

public class User implements Runnable {

    private final PrinterMonitor printerMonitor;
    private final Random randomGenerator = new Random();

    public User(PrinterMonitor printerMonitor) {
        this.printerMonitor = printerMonitor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int printerNo = printerMonitor.acquirePrinter();
                System.out.println("Printer " + printerNo + " working for " + Thread.currentThread().getName());
                sleep(randomGenerator.nextInt(1000));
                System.out.println("Printer " + printerNo + " finished");
                printerMonitor.releasePrinter(printerNo);
            } catch (InterruptedException ignored) {}
        }
    }
}
