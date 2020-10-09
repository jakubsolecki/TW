package zad2;

public class Main {

    public static void main(String[] args) {

        Buffer buffer = new Buffer();
        Producer producer = new Producer(buffer);
        Consumer consumer1 = new Consumer(buffer);
        Consumer consumer2 = new Consumer(buffer);

        Thread producerThread = new Thread(producer);
        Thread consumer1Thread = new Thread(consumer1);
        Thread consumer2Thread = new Thread(consumer2);

        producerThread.start();
        consumer1Thread.start();
        consumer2Thread.start();

//        try {
//            producerThread.join();
//            consumer1Thread.join();
//            consumer2Thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
