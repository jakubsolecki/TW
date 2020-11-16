package pl.jakubsolecki.lab5.task2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MandelbrotCore extends JFrame {

    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int xPos, yPos, threads, tasks;
    private final BufferedImage I;
    private final ExecutorService executor;
    private final ArrayList<Future<ArrayList<PixelRecord2>>> delayedPixelRecordArrays = new ArrayList<>();

    public MandelbrotCore(
            int xPos,
            int yPos,
            int threads,
            int tasks) {

        super("Mandelbrot Set");
        setBounds(100, 100, WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        executor = Executors.newFixedThreadPool(threads);
        this.xPos = xPos;
        this.yPos = yPos;
        this.threads = threads;
        this.tasks = tasks;
        compute();
    }

    private void compute () {
        long startTime = System.nanoTime();

        int chunkXSize = WIDTH / tasks;
        int chunkYSize = HEIGHT / tasks;
        int startX = 0;
        int startY = 0;

        for (int part = 0; part < tasks; part++) {

            delayedPixelRecordArrays.add(
                    executor.submit(
                        new MandelbrotSubsetCalculator(
                            MAX_ITER,
                            ZOOM,
                            startX,
                            startY,
                            startX + chunkXSize,
                            startY + chunkYSize
                        )
                    )
            );

            startX += chunkXSize;
            startY += chunkYSize;
        }

        ArrayList<PixelRecord2> computedPixels = new ArrayList<>();

        for (Future<ArrayList<PixelRecord2>> pr : delayedPixelRecordArrays) {
            try {
                computedPixels.addAll(pr.get());
            } catch (ExecutionException | InterruptedException ignored) {}
        }

        // TODO: move to the upper for (join both fors)
        for (PixelRecord2 pr : computedPixels) {
            int iter = pr.iter();
            I.setRGB(pr.x(), pr.y(), iter | (iter << 8));
        }

        double computingTime = System.nanoTime() - startTime;
        System.out.println(computingTime / 1_000_000_000);
    }

    @Override
    public void paint (Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}
