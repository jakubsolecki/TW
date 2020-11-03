package main.java.task1;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        PrinterMonitor printerMonitor = new PrinterMonitor(10);
        HashSet<Thread> threadSet = new HashSet<>();
        for (int i = 0; i < 30; i++) {
            Thread t = new Thread(new User(printerMonitor));
            t.start();
            threadSet.add(t);
        }


        threadSet.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException ignored) {}
        });
    }
}
