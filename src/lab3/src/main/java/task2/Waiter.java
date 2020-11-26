package main.java.task2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter extends Thread {
    private final Lock lock = new ReentrantLock();
    private final Condition availableTable = lock.newCondition();
    private final Map<Integer, Condition> waitConditions = new HashMap<>();
    private final Set<Integer> pairWaits = new HashSet<>();
    private int eatingGuests = 0;
    private int eatingPair = -1;

    public void acquireTable(int pairNo) {
        lock.lock();
        if(!pairWaits.contains(pairNo)) {
            waitConditions.put(pairNo, lock.newCondition());
            pairWaits.add(pairNo);
            try {
                waitConditions.get(pairNo).await();
            } catch (InterruptedException ignored) {}
        }
        else {
            waitConditions.get(pairNo).signal();
            waitConditions.remove(pairNo);
            pairWaits.remove(pairNo);
        }

        while (eatingGuests != 0 && eatingPair != pairNo) {
            try {
                availableTable.await();
            } catch (InterruptedException ignored) {}
        }

        eatingGuests++;
        eatingPair = pairNo;

        lock.unlock();
    }

    public void releaseTable() {
        lock.lock();
        eatingGuests--;

        if (eatingGuests == 0) {
            availableTable.signalAll();
        }
        lock.unlock();
    }
}
