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

    public FairBuffer(int M) {
        this.BUFFER_SIZE = 2*M;
    }

    @Override
    public void put(int amount) {

    }

    @Override
    public void take(int amount) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }
}
