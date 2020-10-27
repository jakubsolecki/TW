package main.java.zad2;

public class Main {
    public static void main(String[] args) {
        Waiter waiter = new Waiter();

        for (int i = 0; i < 20; i++) {
            new Guest(i, i+1, waiter, 1000).start();
            new Guest(i, i+2, waiter, 1000).start();

        }
    }

}
