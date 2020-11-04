package pl.jakubsolecki.task2;

public interface Buffer {

    void put(int amount);
    void take(int amount);
    int getBufferSize();
}
