package pl.jakubsolecki.lab5.task3;

import java.util.concurrent.Callable;

public class MandelbrotPointCalculator implements Callable<Integer> {

    private final int MAX_ITER;
    private final double cX, cY;

    public MandelbrotPointCalculator(double cX, double cY, int MAX_ITER) {
        this.cX = cX;
        this.cY = cY;
        this.MAX_ITER = MAX_ITER;
    }

    @Override
    public Integer call() {
        double tmp;
        double zx = 0;
        double zy = 0;
        int iter = MAX_ITER;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        return iter;
    }
}
