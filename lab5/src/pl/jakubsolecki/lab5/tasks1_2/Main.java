package pl.jakubsolecki.lab5.tasks1_2;

import pl.jakubsolecki.lab5.TimeMeter;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        int threads = 4;
        int tasks = 40;
        int MAX_ITER = 57000;

        try {
            TimeMeter timeMeter = new TimeMeter("times_"+tasks+"tasks_"+threads+"threads.txt", MAX_ITER);

            for (int i = 0; i < 10; i++) {
                new MandelbrotSetSplitter(
                        MAX_ITER,
                        150,
                        800,
                        600,
                        400,
                        300,
                        threads,
                        tasks,
                        timeMeter
                ).setVisible(true);
            }

            timeMeter.write2File();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
