package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Predicate;

public class TaskTwo {
    private static final int number = 30;
    private static volatile List<String> numbers = new ArrayList<>(number);

    static {
        for (int i = 1; i <= number; i++) {
            numbers.add(String.valueOf(i));
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

    public static void number() {
        int i = 0;
        while (i < number - 1){
            if (numbers.get(i) != null){
                System.out.printf("%s, ", numbers.get(i));
                i++;
            }
        }
        System.out.println(numbers.get(numbers.size() - 1)
        );
    }
    public static void fizzbuzz() {
        replace( i -> i % 5 == 0 && i % 3 == 0,"fizzbuzz");
    }
    public static void buzz() {
        replace( i -> i % 5 == 0,"buzz");
    }
    public static void fizz() {
        replace( i -> i % 3 == 0,"fizz");
    }
    private static void replace(Predicate<Integer> predicate,String toReplace){
        Objects.requireNonNull(toReplace);
        synchronized (TaskTwo.class) {
            for (int i = 1; i <= number; i++) {
                if (predicate.test(i)) {
                    numbers.set(i - 1, toReplace);
                }
            }
        }
    }
}

