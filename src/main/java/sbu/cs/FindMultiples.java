package sbu.cs;

/*
    In this exercise, you must write a multithreaded program that finds all
    integers in the range [1, n] that are divisible by 3, 5, or 7. Return the
    sum of all unique integers as your answer.
    Note that an integer such as 15 (which is a multiple of 3 and 5) is only
    counted once.

    The Positive integer n > 0 is given to you as input. Create as many threads as
    you need to solve the problem. You can use a Thread Pool for bonus points.

    Example:
    Input: n = 10
    Output: sum = 40
    Explanation: Numbers in the range [1, 10] that are divisible by 3, 5, or 7 are:
    3, 5, 6, 7, 9, 10. The sum of these numbers is 40.

    Use the tests provided in the test folder to ensure your code works correctly.
 */

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FindMultiples
{
    public static class Task implements Runnable{
        int[] divisors;
        Set<Integer> results;
        int n;
        int start;
        int end;
        Lock lock;

        public Task(int[] divisors, Set<Integer> results, int n, int start, int end, Lock lock) {
            this.divisors = divisors;
            this.results = results;
            this.n = n;
            this.start = start;
            this.end = end;
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                for (int i = this.start; i <= this.end; i++) {
                    for (int divisor : this.divisors) {
                        if (i % divisor == 0) {
                            this.results.add(i);
                            break;
                        }
                    }
                }
            } finally{
                lock.unlock();
            }
        }
    }

    /*
    The getSum function should be called at the start of your program.
    New Threads and tasks should be created here.
    */
    public static int getSum(int n) {
        int[] divisors = {3, 5, 7};
        Set<Integer> results = new HashSet<>();
        List<Task> taskList = new ArrayList<>();
        List<Thread> threadList = new ArrayList<>();
        Lock lock = new ReentrantLock();
        int range = (int) Math.floor(n / 6);
        //Map<Integer, Integer> start_end = new HashMap<>();
        int start_1 = 1; int end_1 = range; //start_end.put(start_1, end_1);
        int start_2 = end_1 + 1; int end_2 = 2*range; //start_end.put(start_2, end_2);
        int start_3 = end_2 + 1; int end_3 = 3*range; //start_end.put(start_3, end_3);
        int start_4 = end_3 + 1; int end_4 = 4*range; //start_end.put(start_4, end_4);
        int start_5 = end_4 + 1; int end_5 = 5*range; //start_end.put(start_5, end_5);
        int start_6 = end_5 + 1; int end_6 = n; //start_end.put(start_6, end_6);
        Task task_1 = new Task(divisors, results, n, start_1, end_1, lock); taskList.add(task_1);
        Task task_2 = new Task(divisors, results, n, start_2, end_2, lock); taskList.add(task_2);
        Task task_3 = new Task(divisors, results, n, start_3, end_3, lock); taskList.add(task_3);
        Task task_4 = new Task(divisors, results, n, start_4, end_4, lock); taskList.add(task_4);
        Task task_5 = new Task(divisors, results, n, start_5, end_5, lock); taskList.add(task_5);
        Task task_6 = new Task(divisors, results, n, start_6, end_6, lock); taskList.add(task_6);

        for (Task task : taskList){
            Thread thread = new Thread(task);
            threadList.add(thread);
        }

        for (Thread thread : threadList){
            thread.start();
        }

        for (Thread thread : threadList){
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        int sum = 0;
        for (int number : results){
            sum += number;
        }
        return sum;
    }
    public static void main(String[] args) {
        int n = 76293;
        System.out.println(getSum(n));
    }
}