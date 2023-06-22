package sbu.cs;

/*
    In this exercise, you must analyse the following code and use interrupts
    in the main function to terminate threads that run for longer than 3 seconds.

    A thread may run for longer than 3 seconds due the many different reasons,
    including lengthy process times or getting stuck in an infinite loop.

    Take note that you are NOT ALLOWED to change or delete any existing line of code.
 */

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class UseInterrupts
{
    /*
        TODO
         Analyse the following class and add new code where necessary.
         If an object from this type of thread is Interrupted, it must print this:
            "{ThreadName} has been interrupted"
         And then terminate itself.
     */
    public static class SleepThread extends Thread {
        int sleepCounter;

        public SleepThread(int sleepCounter) {
            super();
            this.sleepCounter = sleepCounter;
        }

        @Override
        public void run() {
            System.out.println(this.getName() + " is Active.");
            while (this.sleepCounter > 0)
            {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    Thread.sleep(1000);
                    if (currentThread().isAlive()){
                        System.out.println(currentThread().getName() + " is interrupted because it was running for more than 3 seconds!");
                        currentThread().interrupt();
                        break;
                    }
                } catch (InterruptedException e) {
//                    System.out.println(currentThread().getName() + " is Interrupted!");
                } finally {
                    this.sleepCounter--;
                    System.out.println("Number of sleeps remaining: " + this.sleepCounter);
                }
            }
        }
    }

    /*
        TODO
         Analyse the following class and add new code where necessary.
         If an object from this type of thread is Interrupted, it must print this:
            "{ThreadName} has been interrupted"
         And then terminate itself.
         (Hint: Use the isInterrupted() method)
     */
    public static class LoopThread extends Thread {
        int value;
        public LoopThread(int value) {
            super();
            this.value = value;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(this.getName() + " is Active.");
                for (int i = 0; i < 10; i += 3)
                {
                    System.out.println(i);
                    i -= this.value;
                    if (currentThread().isAlive()){
                        System.out.println(currentThread().getName() + " is interrupted because it was running for more than 3 seconds!");
                        currentThread().interrupt();
                        break;
                    }
                }
            } catch (InterruptedException e) {
//                System.out.println(currentThread().getName() + " is interrupted");
            }
        }
    }

    public static void main(String[] args) {
        SleepThread sleepThread = new SleepThread(5);
        sleepThread.start();

        LoopThread loopThread = new LoopThread(3);
        loopThread.start();

    }
}