package pl.jakubsolecki.task2;

import pl.jakubsolecki.task1.Processor;

public class Main {
    public static void main(String[] args) {
        final int M = 100000;
//        final Buffer buffer = new NaiveBuffer(M);
        final Buffer buffer = new FairBuffer(M);

        for (int i = 0; i < 1000; i++) {
            (new Producer(buffer)).start();
            (new Consumer(buffer)).start();
        }
    }
}
