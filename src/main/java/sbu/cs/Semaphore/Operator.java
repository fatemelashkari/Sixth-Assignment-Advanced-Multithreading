package sbu.cs.Semaphore;

import java.util.concurrent.Semaphore;

public class Operator extends Thread {

    private String name;
    public Operator(String name) {

        super(name);
        this.name = name;
    }
    final int permission = 2;
    Semaphore semaphore = new Semaphore(permission); // in this place we inform that two threads can access to the critical section at the same time
    @Override
    public void run() {
        for (int i = 0; i < 10; i++)
        {
            Resource.accessResource();         // critical section - a Maximum of 2 operators can access the resource concurrently
            try {
                semaphore.acquire(); // the acquire method is used to get the permission for accessing the critical section
                long current_System_Time = System.currentTimeMillis();
                System.out.println(name + " : Is in the critical section, the current System Time : " + current_System_Time);
                sleep(500);
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
