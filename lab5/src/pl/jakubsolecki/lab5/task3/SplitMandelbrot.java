package pl.jakubsolecki.lab5.task3;

import pl.jakubsolecki.lab5.TimeMeter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SplitMandelbrot extends JFrame {

    private final int MAX_ITER;
    private final double ZOOM;
    private final int WIDTH;
    private final int HEIGHT;
    private final int xPos, yPos;
    private final BufferedImage I;
    private final ExecutorService executor;
    private final ArrayList<PixelRecord> pixelRecords = new ArrayList<>();
    private final TimeMeter timeMeter;

    public SplitMandelbrot(
            int MAX_ITER,
            double ZOOM,
            int WIDTH,
            int HEIGHT,
            int xPos,
            int yPos,
            int threads,
            TimeMeter timeMeter
    ) {

        super("BasicMandelbrot Set");
        setBounds(100, 100, WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        executor = Executors.newFixedThreadPool(threads);
        this.MAX_ITER = MAX_ITER;
        this.ZOOM = ZOOM;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.xPos = xPos;
        this.yPos = yPos;
        this.timeMeter = timeMeter;
        compute();
    }

    private void compute () {
        timeMeter.startTime();

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Future<Integer> pointIterations = executor.submit(
                        new MandelbrotPointCalculator(
                                (x - xPos) / ZOOM,
                                (y - yPos) / ZOOM,
                                MAX_ITER
                        )
                );
                pixelRecords.add(new PixelRecord(x, y, pointIterations));
            }
        }

        for (PixelRecord pr : pixelRecords) {
            try {
                int iter = pr.iter().get();
                I.setRGB(pr.x(), pr.y(), iter | (iter << 8));
            } catch (InterruptedException | ExecutionException ignored) {}
        }

        timeMeter.stopTime();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}
