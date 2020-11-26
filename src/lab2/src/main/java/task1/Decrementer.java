package main.java.task1;

public class Decrementer implements Runnable {

    private final Counter counter;
    private final MyBinarySemaphore myBinarySemaphore;

    public Decrementer(Counter counter, MyBinarySemaphore myBinarySemaphore) {
        this.counter = counter;
        this.myBinarySemaphore = myBinarySemaphore;
    }

    @Override
    public void run() {

        for (int i = 0; i < 100000; i++) {
            myBinarySemaphore.acquire();
            counter.decrement();
            myBinarySemaphore.release();
        }
    }
}
