package pl.jakubsolecki.task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StatisticsGenerator extends Thread {

//    private final int BUFFER_SIZE;
    private int currentBufferSize = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition waitForGoods = lock.newCondition();
    private final Condition waitForPlace = lock.newCondition();

}
