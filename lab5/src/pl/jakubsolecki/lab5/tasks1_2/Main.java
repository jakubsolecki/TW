package pl.jakubsolecki.lab5.tasks1_2;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            TimeMeter timeMeter = new TimeMeter("times_4tasks_4threads.txt");

            for (int i = 0; i < 10; i++) {
                new MandelbrotSetSplitter(
                        5700,
                        150,
                        800,
                        600,
                        400,
                        300,
                        4,
                        4,
                        timeMeter
                ).setVisible(true);
            }

            timeMeter.write2File();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
