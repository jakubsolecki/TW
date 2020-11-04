package pl.jakubsolecki.task2;

import pl.jakubsolecki.task2.model.Operation;
import pl.jakubsolecki.task2.model.Portion;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsGenerator extends Thread {

    private final Map<Long, Operation> dataMap = new HashMap<>();
    private final List<Operation> waitingTimesList = new ArrayList<>();
    private final Map<Long, Portion> resultMap = new HashMap<>();

    public synchronized void startTime(int amount) {
        dataMap.put(Thread.currentThread().getId(), new Operation(amount));
    }

    public synchronized void stopTime() {
        Operation operation = dataMap.get(Thread.currentThread().getId());
        operation.endTime = System.nanoTime();
        waitingTimesList.add(operation);
        dataMap.remove(Thread.currentThread().getId());
    }

    private synchronized void fillPortions(){
        for (Operation operation : waitingTimesList){
            if (resultMap.containsKey(operation.amount)){
                resultMap.get(operation.amount).timeList.add(operation.endTime - operation.startTime);
            } else {
                resultMap.put(operation.amount, new Portion(operation.endTime - operation.startTime, operation.amount));
            }
        }

        resultMap.values().forEach(Portion::countAvgTime);
    }

    @Override
    public void run() {
        super.run();

        try {
            sleep(20000);
        } catch (InterruptedException ignored) {}
        this.fillPortions();
        try {
            FileWriter fileWriter = new FileWriter("times.txt");
            resultMap.values().forEach(val -> {
                try {
                    fileWriter.write(val.portionSize + ";" + val.averageTime.toString().replace('.', ',') + "\n");
                } catch (IOException ignored) {}
            });
            fileWriter.close();
        } catch (IOException ignored) {}

        System.exit(0);
    }

}
