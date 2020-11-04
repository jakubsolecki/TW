package pl.jakubsolecki.task2;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NaiveBuffer implements Buffer {

    private final int BUFFER_SIZE;
    private int currentBufferSize = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition waitForGoods = lock.newCondition();
    private final Condition waitForPlace = lock.newCondition();
    private final StatisticsGenerator statisticsGenerator = new StatisticsGenerator();

    public NaiveBuffer(int M) {
        this.BUFFER_SIZE = 2*M;
        statisticsGenerator.start();
    }

    @Override
    public void put(int amount) {
        lock.lock();

        statisticsGenerator.startTime(amount);
        while (currentBufferSize + amount > BUFFER_SIZE) {
            try {
                waitForPlace.await();
            } catch (InterruptedException ignored) {}
        }
        currentBufferSize += amount;
        waitForGoods.signalAll();
        System.out.println("Producer added " + amount + " units.\n Current amount of goods: " + currentBufferSize + "\n");

        statisticsGenerator.stopTime();
        lock.unlock();
    }

    @Override
    public void take(int amount) {
        lock.lock();

        statisticsGenerator.startTime(amount);
        while (currentBufferSize < amount) {
            try {
                waitForGoods.await();
            } catch (InterruptedException ignored) {}
        }
        currentBufferSize -= amount;
        waitForPlace.signalAll();
        System.out.println("Consumer took " + amount + " units.\n Current amount of goods: " + currentBufferSize + "\n");

        statisticsGenerator.stopTime();
        lock.unlock();
    }

    @Override
    public int getBufferSize() {
        return BUFFER_SIZE;
    }
}
