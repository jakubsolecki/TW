package pl.jakubsolecki.task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairBuffer implements Buffer {

    private final int BUFFER_SIZE;
    private int currentBufferSize;
    private final Lock lock = new ReentrantLock();
    private final Condition waitForFirstProducer = lock.newCondition();
    private final Condition waitAsFirstProducer = lock.newCondition();
    private final Condition waitForFirstConsumer = lock.newCondition();
    private final Condition waitAsFirstConsumer = lock.newCondition();
    private boolean firstConsumerAvailable = true;
    private boolean firstProducerAvailable = true;
    private final StatisticsGenerator statisticsGenerator = new StatisticsGenerator();

    public FairBuffer(int M) {
        this.BUFFER_SIZE = 2*M;
        statisticsGenerator.start();
    }

    @Override
    public void put(int amount) {
        lock.lock();

        statisticsGenerator.startTime(amount);
        while (!firstProducerAvailable) {
            try {
                waitForFirstProducer.await();
            } catch (InterruptedException ignored) {}
        }
        System.out.println("Producer waiting for its turn");

        firstProducerAvailable = false;
        while (currentBufferSize + amount > BUFFER_SIZE) {
            try {
                waitAsFirstProducer.await();
            } catch (InterruptedException ignored) {}
        }
        currentBufferSize += amount;
        System.out.println("Producer added " + amount + " units\n Current buffer size: " + currentBufferSize + "\n");
        firstProducerAvailable = true;
        waitAsFirstConsumer.signal();
        waitForFirstProducer.signal();

        statisticsGenerator.stopTime();
        lock.unlock();
    }

    @Override
    public void take(int amount) {
        lock.lock();

        statisticsGenerator.startTime(amount);
        while (!firstConsumerAvailable) {
            try {
                waitForFirstConsumer.await();
            } catch (InterruptedException ignored) {}
        }
        System.out.println("Producer waiting for its turn");

        firstConsumerAvailable = false;
        while (currentBufferSize < amount) {
            try {
                waitAsFirstConsumer.await();
            } catch (InterruptedException ignored) {}
        }
        currentBufferSize -= amount;
        System.out.println("Consumer took " + amount + " units\n Current buffer size: " + currentBufferSize + "\n");
        firstConsumerAvailable = true;
        waitAsFirstProducer.signal();
        waitForFirstConsumer.signal();

        statisticsGenerator.stopTime();
        lock.unlock();
    }

    @Override
    public int getBufferSize() {
        return BUFFER_SIZE;
    }
}
