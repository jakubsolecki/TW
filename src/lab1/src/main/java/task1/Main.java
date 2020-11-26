package task1;

public class Main {
    public static void main(String[] args) {

        int counterValue;
        int raceOccurrences = 0;
        int i = 0;

        while (raceOccurrences < 5) {

            i++;

//            Counter counter = new Counter();
            Counter counter = new SynchronizedCounter();
            Incrementer incrementer = new Incrementer(counter);
            Decrementer decrementer = new Decrementer(counter);

            Thread t1 = new Thread(incrementer,"Incrementer");
            Thread t2 = new Thread(decrementer,"Decrementer");

            t1.start();
            t2.start();

            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            counterValue = counter.getValue();

            System.out.println(i + ": " + "Counter's value: " + counterValue);

            if(counterValue != 0) {
                raceOccurrences++;
            }
        }
    }
}
