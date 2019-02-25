import java.util.concurrent.atomic.AtomicBoolean;

public class prova extends Thread {
    private AtomicBoolean keepRunning = true;
    @Override
    public void run() { long count = 0;
        while (keepRunning) { count++;
        }
        System.out.println("Thread terminated." + count); }
    public static void main(String[] args) throws InterruptedException { System.out.println("Program started.");
        prova t = new prova();
        t.start();
        Thread.sleep(1000);
        t.keepRunning = false; System.out.println("keepRunning set to false.");
    }
}
