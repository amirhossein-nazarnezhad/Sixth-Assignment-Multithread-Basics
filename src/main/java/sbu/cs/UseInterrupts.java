package sbu.cs;

/*

 */

import java.sql.Time;
import java.time.Duration;
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
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    System.out.println("{" + currentThread().getName() + "}" + " has been interrupted");
                    break;
                }
                finally {
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
            System.out.println(this.getName() + " is Active.");

            for (int i = 0; i < 10 && !Thread.currentThread().isInterrupted() ; i += 3)
            {
                i -= this.value;
            }
            if(Thread.currentThread().isInterrupted())
            {
                System.out.println("{" +  Thread.currentThread().getName() + "}" + " has been interrupted");
            }
        }
    }

/*
    You can add new code to the main function. This is where you must utilize interrupts.
    No existing line of code should be changed or deleted.
 */
    public static void main(String[] args) {
        SleepThread sleepThread = new SleepThread(5);
        Instant start = Instant.now();
        sleepThread.start();
        while (sleepThread.isAlive())
        {
           if(Duration.between(start, Instant.now()).getSeconds() >  3)
           {
               sleepThread.interrupt();
           }

        }

        // TODO  Check if this thread runs for longer than 3 seconds (if it does, interrupt it)

        LoopThread loopThread = new LoopThread(3);
        loopThread.start();
        while (loopThread.isAlive())
        {
            if(Duration.between(start, Instant.now()).getSeconds() >  3)
            {
                loopThread.interrupt();
            }

        }

        // TODO  Check if this thread runs for longer than 3 seconds (if it does, interrupt it)

    }
}
