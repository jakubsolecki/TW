package pl.jakubsolecki.lab5.task1;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SplitMandelbrot extends JFrame {

    private final int MAX_ITER = 570;
    private final double ZOOM = 200;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int xPos, yPos;
    private final BufferedImage I;
    private final ExecutorService executor;
    private final ArrayList<PixelRecord> pixelRecords = new ArrayList<>();

    public SplitMandelbrot(int threads, int xPos, int yPos) {
        super("BasicMandelbrot Set");
        setBounds(100, 100, WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        executor = Executors.newFixedThreadPool(threads);
        this.xPos = xPos;
        this.yPos = yPos;
        compute();
    }

    private void compute () {
        long startTime = System.nanoTime();

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

        double computingTime = System.nanoTime() - startTime;
        System.out.println(computingTime / 1_000_000_000);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        new SplitMandelbrot(4, 400, 300).setVisible(true);
    }
}
