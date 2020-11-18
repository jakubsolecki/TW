package pl.jakubsolecki.lab5.task3;

import pl.jakubsolecki.lab5.TimeMeter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int threads = 10;
        int MAX_ITER = 57000;

        try {
            TimeMeter timeMeter = new TimeMeter("times_"+threads+"threads"+"_taskpoints.txt", MAX_ITER);

            for (int i = 0; i < 10; i++) {
                new SplitMandelbrot(
                        MAX_ITER,
                        150,
                        800,
                        600,
                        400,
                        300,
                        threads,
                        timeMeter
                ).setVisible(true);
            }

            timeMeter.write2File();

        }  catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
