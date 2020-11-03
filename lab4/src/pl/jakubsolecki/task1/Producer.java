package pl.jakubsolecki.task1;

import java.util.Random;

public class Producer extends Thread {
    private final Buffer buffer;
    private final Random randomGenerator = new Random();

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            buffer.produce();
            try {
                sleep(randomGenerator.nextInt(100));
            } catch (InterruptedException ignored) {}
        }
    }
}
