package pl.jakubsolecki.lab5.tasks1_2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TimeMeter {

    private final String FILE_NAME;
    private final FileWriter fileWriter;
    private final ArrayList<Double> workTimes = new ArrayList<>();
    private final Lock lock = new ReentrantLock();

    private Long tmpStartTime;

    public TimeMeter(String FILE_NAME) throws IOException {
        this.FILE_NAME = FILE_NAME;
        this.fileWriter = new FileWriter(FILE_NAME);
    }

    public void startTime() {
        lock.lock();
        tmpStartTime = System.nanoTime();
    }

    public void stopTime() {
        double elapsedTime = System.nanoTime() - tmpStartTime;
        System.out.println(elapsedTime / 1_000_000_000);
        workTimes.add(elapsedTime / 1_000_000_000); // convert measured time to seconds
        lock.unlock();
    }

    public void write2File () throws IOException {
        for (Double time : workTimes) {
            fileWriter.write(time.toString().replace('.', ',') + "\n");
        }
        fileWriter.close();
    }

    public void close() throws IOException {
        fileWriter.close();
    }
}
