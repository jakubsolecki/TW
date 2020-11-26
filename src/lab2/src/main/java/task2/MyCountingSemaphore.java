package main.java.task2;

public class MyCountingSemaphore{

    private int counter;

    public MyCountingSemaphore(int counter) {
        this.counter = counter;
    }

    public synchronized void acquire() {
        try {
            while (counter <= 0) {
                wait();
            }
        } catch (InterruptedException ignored) {}

        counter--;
    }

    public synchronized void release() {
        counter++;
        notifyAll();
    }
}
