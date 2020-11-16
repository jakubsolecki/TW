package pl.jakubsolecki.lab5.warm_up;

import java.util.concurrent.Future;

public record PixelRecord (int x, int y, Future<Integer> iter) {}
