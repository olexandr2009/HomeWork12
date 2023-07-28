package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskTwo {
    private static  int number = new Scanner(System.in).nextInt();
    private static volatile List<String> toPrint = new ArrayList<>();

    static {
        for (int i = 1; i <= number; i++) {
            toPrint.add(String.valueOf(i));
        }
    }

    public static void main(String[] args){
        startTask();
    }
    private static void startTask(){
        Thread threadA = new Thread(TaskTwo::fizz, "A");
        Thread threadB = new Thread(TaskTwo::buzz, "B");
        Thread threadC = new Thread(TaskTwo::fizzbuzz, "C");
        Thread threadD = new Thread(TaskTwo::number, "D");

        threadA.start();
        threadB.start();
        threadC.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        threadD.start();
    }

    private static void number() {
        System.out.println(
                toPrint.stream()
                .collect(Collectors.joining(", "))
        );
    }

    private static void fizzbuzz() {
        replace( i -> i % 5 == 0 && i % 3 == 0,"fizzbuzz");
    }

    private static void buzz() {
        replace( i -> i % 5 == 0,"buzz");
    }

    private static void fizz() {
        replace( i -> i % 3 == 0,"fizz");
    }
    private static void replace(Predicate<Integer> predicate,String toReplace){
        synchronized (TaskTwo.class) {
            for (int i = 1; i <= number; i++) {
                if (predicate.test(i)) {
                    toPrint.set(i - 1, toReplace);
                }
            }
        }
    }
}

