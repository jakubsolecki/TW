package pl.jakubsolecki.task2.model;

public class Operation {

    public final long amount;
    public final long startTime;
    public long endTime;

    public Operation(int amount) {
        this.amount = amount;
        this.startTime = System.nanoTime();
    }
}
