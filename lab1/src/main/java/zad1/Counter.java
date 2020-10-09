package zad1;

import lombok.Getter;

@Getter
public class Counter {

    protected int value = 0;

    public void increment() {
        value++;
    }

    public void decrement() {
        value--;
    }
}
