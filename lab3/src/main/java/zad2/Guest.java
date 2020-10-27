package main.java.zad2;

import java.util.Random;

public class Guest extends Thread {
    private final int pairNo;
    private final int guestNo;
    private final Waiter waiter;
    private final int timeSeed;
    private final Random randomGenerator = new Random();


    public Guest(int pairNo, int guestNo, Waiter waiter, int timeSeed) {
        this.pairNo = pairNo;
        this.guestNo = guestNo;
        this.waiter = waiter;
        this.timeSeed = timeSeed;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                sleep(randomGenerator.nextInt(timeSeed));
                System.out.println("Guest " + guestNo + " arrived. Pair no.: " + pairNo);
                waiter.acquireTable(pairNo);

                System.out.println("Pair " + pairNo + " is eating");
                sleep(randomGenerator.nextInt(timeSeed));

                System.out.println("Pair " + pairNo + " finished. Guest " + guestNo + " is leaving");
                waiter.releaseTable();
            } catch (InterruptedException ignored) {}
        }
    }
}
