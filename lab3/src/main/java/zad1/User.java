package main.java.zad1;

public class User implements Runnable {

    private final PrinterMonitor printerMonitor;

    public User(PrinterMonitor printerMonitor) {
        this.printerMonitor = printerMonitor;
    }

    @Override
    public void run() {
        while (true) {

        }
    }
}
