package zad1;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Decrementer implements Runnable{

    private final Counter counter;

    @Override
    public void run() {

        for (int i = 0; i < 10000000; i++) {
            counter.decrement();
        }
    }
}
