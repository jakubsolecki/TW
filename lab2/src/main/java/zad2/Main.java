package main.java.zad2;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        MyCountingSemaphore myCountingSemaphore = new MyCountingSemaphore(3);
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread t1 = new Thread(new Customer(myCountingSemaphore));
            threadList.add(t1);
            t1.start();
        }

        threadList.forEach(t -> {
                    try {
                        t.join();
                    } catch (InterruptedException ignored) {}
                });
    }
}
