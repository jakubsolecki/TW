package pl.jakubsolecki.task2;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NaiveBuffer implements Buffer {

    private final int BUFFER_SIZE;
    private int currentBufferSize = 0;
//    private final int PRODUCERS;
//    private final int CONSUMERS;
    private final Lock lock = new ReentrantLock();
    private final Condition waitForGoods = lock.newCondition();
    private final Condition waitForPlace = lock.newCondition();
    private int waitingConsumers = 0;
    private int waitingProducers = 0;

    public NaiveBuffer(int M/*, int PRODUCERS, int CONSUMERS*/) {
        this.BUFFER_SIZE = 2*M;
//        this.PRODUCERS = PRODUCERS;
//        this.CONSUMERS = CONSUMERS;
    }

    @Override
    public void put(int amount) {
        lock.lock();

//        waitingProducers++;
        while (currentBufferSize + amount > BUFFER_SIZE) {
            try {
                waitForPlace.await();
            } catch (InterruptedException ignored) {}
        }
        currentBufferSize += amount;
        waitForGoods.signalAll();
        System.out.println("Producer added " + amount + " units.\n Current amount of goods: " + currentBufferSize + "\n");

        lock.unlock();
    }

    @Override
    public void take(int amount) {
        lock.lock();

        while (currentBufferSize < amount) {
            try {
                waitForGoods.await();
            } catch (InterruptedException ignored) {}
        }
        currentBufferSize -= amount;
        waitForPlace.signalAll();
        System.out.println("Consumer took " + amount + " units.\n Current amount of goods: " + currentBufferSize + "\n");

        lock.unlock();
    }

    @Override
    public int getBufferSize() {
        return BUFFER_SIZE;
    }
}
