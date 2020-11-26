package pl.jakubsolecki.lab5.tasks1_2;

import pl.jakubsolecki.lab5.TimeMeter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MandelbrotSetSplitter extends JFrame {

    private final int MAX_ITER;
    private final double ZOOM;
    private final int WIDTH;
    private final int HEIGHT;
    private final int xPos, yPos, threads, tasks;
    private final BufferedImage I;
    private final ExecutorService executor;
    private final ArrayList<Future<ArrayList<PixelRecord2>>> delayedPixelRecordArrays = new ArrayList<>();
    private final TimeMeter timeMeter;

    public MandelbrotSetSplitter(
            int MAX_ITER,
            double ZOOM,
            int WIDTH,
            int HEIGHT,
            int xPos,
            int yPos,
            int threads,
            int tasks,
            TimeMeter timeMeter
    ) {

        super("Mandelbrot Set");
        setBounds(100, 100, WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        executor = Executors.newFixedThreadPool(threads);
        this.MAX_ITER = MAX_ITER;
        this.ZOOM = ZOOM;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.xPos = xPos;
        this.yPos = yPos;
        this.threads = threads;
        this.tasks = tasks;
        this.timeMeter = timeMeter;
        compute();
    }

    private void compute () {

    timeMeter.startTime();

        int chunkYSize = HEIGHT / tasks;
        int startY = 0;

        for (int part = 0; part < tasks; part++) {

            delayedPixelRecordArrays.add(
                    executor.submit(
                        new MandelbrotSubsetCalculator(
                            MAX_ITER,
                            ZOOM,
                            xPos,
                            yPos,
                            startY,
                            startY + chunkYSize
                        )
                    )
            );

            startY += chunkYSize;
        }

        for (Future<ArrayList<PixelRecord2>> fpr : delayedPixelRecordArrays) {
            try {
                for (PixelRecord2 pr : fpr.get()) {
                    int iter = pr.iter();
                    I.setRGB(pr.x(), pr.y(), iter | (iter << 8));
                }
            } catch (ExecutionException | InterruptedException ignored) {}
        }

        timeMeter.stopTime();
    }

    @Override
    public void paint (Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}
