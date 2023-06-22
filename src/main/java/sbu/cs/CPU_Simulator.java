package sbu.cs;

import java.util.*;

public class CPU_Simulator
{
    public static class Task implements Runnable {
        int processingTime;
        String ID;
        public Task(String ID, int processingTime) {
            this.processingTime = processingTime;
            this.ID = ID;
        }

        public String getID() {
            return ID;
        }

        public int getProcessingTime() {
            return processingTime;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(this.processingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static ArrayList<String> startSimulation(ArrayList<Task> tasks){
        ArrayList<String> executedTasks = new ArrayList<>();
        Collections.sort(tasks, taskTime);
        for (int i = 0 ; i < tasks.size(); i++){
            Thread thread = new Thread(tasks.get(i));
            thread.start();
            try {
                thread.join(tasks.get(i).getProcessingTime());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            executedTasks.add(tasks.get(i).getID());
        }
        return executedTasks;
    }
    public static Comparator<Task> taskTime = new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            int time_1 = o1.getProcessingTime();
            int time_2 = o2.getProcessingTime();
            return time_1 - time_2;
        }
    };
    public static void main(String[] args) {
        Task task_1 = new Task("123", 1000);
        Task task_2 = new Task("234", 2000);
        Task task_3 = new Task("789", 500);
        ArrayList <Task> tasks = new ArrayList<>();
        tasks.add(task_1);
        tasks.add(task_2);
        tasks.add(task_3);
        System.out.println(startSimulation(tasks));
    }
}