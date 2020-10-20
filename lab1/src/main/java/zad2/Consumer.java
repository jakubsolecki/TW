package zad2;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Consumer implements Runnable {

    private final Buffer buffer;
    private static final int ILOSC = 50;

    public void run() {

        for(int i = 0;  i < ILOSC; i++) {
            String message = buffer.take();
            System.out.println(Thread.currentThread().getName() + " received: " + message);
        }

    }
}
