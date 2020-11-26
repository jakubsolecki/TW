package task1;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Incrementer implements Runnable{

    protected final Counter counter;

    @Override
    public void run() {

        for (int i = 0; i < 10000000; i++) {
             counter.increment();
        }
    }
}
