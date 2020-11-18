package pl.jakubsolecki.lab5.task3;

import pl.jakubsolecki.lab5.TimeMeter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int threads = 1;

        try {
            TimeMeter timeMeter = new TimeMeter("times_"+threads+"threads"+"_taskpoints");

            for (int i = 0; i < 10; i++) {
                new SplitMandelbrot(threads, 400, 300, timeMeter).setVisible(true);
            }

            timeMeter.write2File();
        }  catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
