package pl.jakubsolecki.task2;

import java.util.Random;

public class Consumer extends Thread {

    private final int MAX_PORTION_SIZE;
    private final Buffer buffer;
    private final Random randomGenerator = new Random();

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
        this.MAX_PORTION_SIZE = buffer.getBufferSize()/2;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            buffer.take(randomGenerator.nextInt(MAX_PORTION_SIZE) + 1);
            try {
                sleep(randomGenerator.nextInt(200));
            } catch (InterruptedException ignored) {}
        }
    }
}
