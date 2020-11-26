package pl.jakubsolecki.task1;

public class Main {
    public static void main(String[] args) {
        int PROCESSORS = 5;
        final int BUFFER_SIZE = 10;
        final Buffer buffer = new Buffer(BUFFER_SIZE, PROCESSORS);
        final Producer producer = new Producer(buffer);
        final Consumer consumer = new Consumer(buffer);

        producer.start();
        for (int i = 0; i < PROCESSORS; i++) {
            (new Processor(i, buffer)).start();
        }
        consumer.start();
    }
}
