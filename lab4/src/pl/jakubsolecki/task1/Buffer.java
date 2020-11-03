package pl.jakubsolecki.task1;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final int bufferSize;
    private final int[] bufferArray;
    private final int processors;
    private final Lock lock = new ReentrantLock();
    private final Condition waitForPut = lock.newCondition();
    private final Condition[] waitForProcessor;
    private final Condition waitForConsumer = lock.newCondition();

    private int producerIndex;
    private int consumerIndex;
    private final int[] processorsIndexes;

    public Buffer(int bufferSize, int processors) {
        this.bufferSize = bufferSize;
        this.bufferArray = new int[bufferSize];
        this.processors = processors;
        this.waitForProcessor = new Condition[processors];
        this.processorsIndexes = new int[processors];

        for (int i = 0; i < bufferSize; i++) {
            bufferArray[i] = -1;
        }

        for (int i = 0; i < processors; i++) {
            waitForProcessor[i] = lock.newCondition();
            processorsIndexes[i] = 0;
        }
    }

    private boolean indexNotReady(int number, int lastIndex) {
        return bufferArray[lastIndex] != number;
    }

    public void produce() {
        lock.lock();
        int currentIndex = producerIndex;
        while (indexNotReady(-1, currentIndex)) {
                try {
                    waitForConsumer.await();
                } catch (InterruptedException ignored) {}
        }
        bufferArray[currentIndex] = 0;
        producerIndex = currentIndex +1 != bufferSize ? currentIndex+1 : 0;
        System.out.println("Producer at index: " + currentIndex);
        System.out.println(Arrays.toString(bufferArray) + "\n");
        waitForProcessor[0].signalAll();
        lock.unlock();
    }

    public void process(int processorNo) {
        lock.lock();
        int currentIndex = processorsIndexes[processorNo];
        while (indexNotReady(processorNo, currentIndex)) {
            try {
                waitForProcessor[processorNo].await();
            } catch (InterruptedException ignored) {}
        }
        bufferArray[currentIndex] = processorNo+1;
        processorsIndexes[processorNo] = currentIndex+1 != bufferSize ? currentIndex+1 : 0;
        System.out.println("Processor " + processorNo + " at index: " + currentIndex);
        System.out.println(Arrays.toString(bufferArray) + "\n");
        if (processorNo < processors - 1) {
            waitForProcessor[processorNo + 1].signalAll();
        } else {
            waitForPut.signalAll();
        }
        lock.unlock();
    }

    public void consume() {
        lock.lock();
        int currentIndex = consumerIndex;
        while (indexNotReady(processors, currentIndex)) {
            try {
                waitForPut.await();
            } catch (InterruptedException ignored) {}
        }
        bufferArray[currentIndex] = -1;
        consumerIndex = currentIndex +1 != bufferSize ? currentIndex +1 : 0;
        System.out.println("Consumer ate at index: " + currentIndex);
        System.out.println(Arrays.toString(bufferArray) + "\n");
        waitForConsumer.signalAll();
        lock.unlock();
    }


}
