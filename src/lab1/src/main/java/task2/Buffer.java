package task2;

public class Buffer {

    private String message;
    private boolean ready = false;

    public synchronized void put(String message) {
        while(ready) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }

        this.message = message;
        changeStatus(true);
    }

    public synchronized String take() {
        while(!ready) {
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }

        changeStatus(false);
        return message;
    }

    private synchronized void changeStatus(boolean ready) {
        this.ready = ready;
        notifyAll();
    }
}
