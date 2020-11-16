package pl.jakubsolecki.lab5.task1;

import java.util.concurrent.Future;

public record PixelRecord (int x, int ym, Future<Integer> iter) {}
