package main.java.task1;

public class MyBinarySemaphore {
    private boolean opened = true;

    public synchronized void acquire() {
        try {
            while (!opened) {
                wait();
            }
        } catch (InterruptedException ignored) {}

        opened = false;
//        changeStatus(false);

    }

    public synchronized void release() {

        opened = true;
        notifyAll();
//        changeStatus(true);
    }
//
//    private synchronized void changeStatus(boolean newStatus) {
//        opened = newStatus;
//        notifyAll();
//    }
}
