package main.java.zad1;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        MyBinarySemaphore myBinarySemaphore = new MyBinarySemaphore();
        Incrementer incrementer = new Incrementer(counter, myBinarySemaphore);
        Decrementer decrementer = new Decrementer(counter, myBinarySemaphore);

        int errorCounter = 0;
        int val;

        while(errorCounter < 6) {
            Thread thread1 = new Thread(incrementer);
            Thread thread2 = new Thread(decrementer);

            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();

            val = counter.getValue();
            if (val != 0) {
                errorCounter++;
            }

            System.out.println(val);
        }
    }
}
