package pl.jakubsolecki.task1;

import java.util.Random;

public class Processor extends Thread {

    private final int processorNo;
    private final Buffer buffer;
    private final Random randomGenerator = new Random();

    public Processor(int processorNo, Buffer buffer) {
        this.processorNo = processorNo;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            buffer.process(processorNo);
            try {
                sleep(randomGenerator.nextInt(100));
            } catch (InterruptedException ignored) {}
        }
    }
}
