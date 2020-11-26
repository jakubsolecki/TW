package pl.jakubsolecki.task2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Portion {

    public final List<Long> timeList;
    public Double averageTime;
    public final Long portionSize;

    public Portion(Long time, long portionSize) {
        timeList = new ArrayList<>();
        timeList.add(time);
        this.portionSize = portionSize;
    }

    public void countAvgTime() {
        averageTime = timeList.stream()
                .collect(Collectors.summarizingLong(Long::longValue))
                .getAverage();
    }


}
