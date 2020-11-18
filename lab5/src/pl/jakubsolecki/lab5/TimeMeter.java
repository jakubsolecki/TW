package pl.jakubsolecki.lab5;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TimeMeter {

    private final String FILE_NAME;
    private final FileWriter fileWriter;
    private final PrintWriter printWriter;
    private final List<Double> workTimes = new ArrayList<>();
    private final Lock lock = new ReentrantLock(); // just in case

    private Long tmpStartTime;

    public TimeMeter(String FILE_NAME) throws IOException {
        this.FILE_NAME = FILE_NAME;
        this.fileWriter = new FileWriter(FILE_NAME);
        this.printWriter = new PrintWriter(fileWriter);
    }

    public void startTime() {
        lock.lock();
        tmpStartTime = System.nanoTime();
    }

    public void stopTime() {
        double elapsedTime = System.nanoTime() - tmpStartTime;
        System.out.println(elapsedTime / 1_000_000_000);
        workTimes.add(elapsedTime / 1_000_000_000);  // convert measured time to seconds
        lock.unlock();
    }

    public void write2File () {

        DoubleSummaryStatistics statistics = workTimes.stream()
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        Double mean = statistics.getAverage();
        System.out.println("\nAvg: " + mean.toString());

        double standardDeviation = workTimes.stream()
                .map(a -> Math.pow(a - mean, 2))
                .mapToDouble(Double::doubleValue).sum() / workTimes.size();

        standardDeviation = Math.sqrt(standardDeviation);

        for (Double time : workTimes) {
            printWriter.println(time.toString().replace('.', ','));
        }

        printWriter.println();
        printWriter.println("Average: " + mean.toString().replace('.', ','));
        printWriter.println("Standard deviation: " + Double.toString(standardDeviation).replace('.', ','));
        printWriter.close();
    }
}
