/**
 * Created by mchinnaswamy on 9/2/2014.
 */
public class InterruptDemo extends Thread {

    String tName;

    public InterruptDemo(String tName) {
        super();
        this.tName = tName;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 2; i++) {
            try {
                Thread.sleep(5000);
                System.out.println("Thread " + this.tName + " interrupted?" + this.isInterrupted() + " i = " + i);
                if (Thread.interrupted()) {
                    System.out.println("Thread is interrupted : " + this.tName);
                } else {
                    System.out.println("Normal thread : " + this.tName);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String args[]) {
        InterruptDemo t1 = new InterruptDemo("T1");
        InterruptDemo t2 = new InterruptDemo("T2");

        t1.start();
        t1.interrupt();


        t2.start();
    }
}
