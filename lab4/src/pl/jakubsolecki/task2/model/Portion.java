package pl.jakubsolecki.task2.model;

import java.util.ArrayList;
import java.util.List;

public class Portion {

    private List<Long> timeList;
    private Double averageTime;
    private Long portionSize;

    public Portion(Long time, long portionSize) {
        timeList = new ArrayList<>();
        timeList.add(time);
        this.portionSize = portionSize;
    }


}
