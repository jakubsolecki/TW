package zad1;

import lombok.Getter;

@Getter
public class SynchronizedCounter extends Counter {

    @Override
    public synchronized void increment() {
        value++;
    }

    @Override
    public synchronized void decrement() {
        value--;
    }
}
