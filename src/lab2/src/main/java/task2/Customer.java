package main.java.task2;

import java.util.Random;

public class Customer implements Runnable {

    private final MyCountingSemaphore myCountingSemaphore;
    private final Random generator = new Random();

    public Customer(MyCountingSemaphore myCountingSemaphore) {
        this.myCountingSemaphore = myCountingSemaphore;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {
            myCountingSemaphore.acquire();
            System.out.println(Thread.currentThread().getName() + ": I took shopping trolley. Imma shop a bit");

            try {
                Thread.sleep(generator.nextInt(1000));
            } catch (InterruptedException ignored) {}

            myCountingSemaphore.release();
            System.out.println(Thread.currentThread().getName() + ": I've finished. See ya!");
        }
    }
}
