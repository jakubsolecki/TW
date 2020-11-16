package pl.jakubsolecki.lab5.tasks1_2;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class MandelbrotSubsetCalculator implements Callable<ArrayList<PixelRecord2>> {

    private final int MAX_ITER, xPos, yPos, maxY, startY;
    private final double ZOOM;
    private final ArrayList<PixelRecord2> pixelRecords = new ArrayList<>();

    public MandelbrotSubsetCalculator (
            int MAX_ITER,
            double ZOOM,
            int xPos,
            int yPos,
            int startY,
            int maxY
    ) {
        this.MAX_ITER = MAX_ITER;
        this.ZOOM = ZOOM;
        this.xPos = xPos;
        this.yPos = yPos;
        this.maxY = maxY;
        this.startY = startY;
    }

    @Override
    public ArrayList<PixelRecord2> call() {

        double tmp, zx, zy, cX, cY;

        for (int y = startY; y < maxY; y++) {
            for (int x = 0; x < 800; x++) {
                zx = zy = 0;
                cX = (x - xPos) / ZOOM;
                cY = (y - yPos) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                pixelRecords.add(new PixelRecord2(x, y, iter));
            }
        }

        return pixelRecords;
    }
}
