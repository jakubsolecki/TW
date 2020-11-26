package pl.jakubsolecki.task1;

import java.util.Random;

public class Consumer extends Thread {

    private final Buffer buffer;
    private final Random randomGenerator = new Random();

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            buffer.consume();
            try {
                sleep(randomGenerator.nextInt(100));
            } catch (InterruptedException ignored) {}
        }
    }
}
