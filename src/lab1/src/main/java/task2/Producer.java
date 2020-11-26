package task2;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Producer implements Runnable {

    private final Buffer buffer;
    private static final int ILOSC = 100;

    public void run() {

        for(int i = 0;  i < ILOSC;   i++) {
            buffer.put("message " + i);
        }
    }
}
