package org.example;

import java.time.LocalTime;

import static java.lang.Thread.*;

public class TaskOne {
    public static void main(String[] args){
        Thread thread1 = new Thread(TaskOne::printTimeOneSec);
        Thread thread2 = new Thread(TaskOne::printTimeFiveSec);

        thread1.start();
        thread2.start();
    }

    public static void printTimeFiveSec() {
        while(!currentThread().isInterrupted()){
            try {
                sleep(5000);
                System.out.println("Минуло 5 секунд!");
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public static void printTimeOneSec() {
        StringBuilder sb = new StringBuilder();
        while(!currentThread().isInterrupted()){
            try {
                sleep(1000);
                sb.append(LocalTime.now().toString(), 0, 8);
                System.out.println(sb);
                sb.delete(0, sb.length());
            } catch (InterruptedException e) {
                return;
            }
        }
    }

}
