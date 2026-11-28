
package com.java.multi_threading;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MultiThreadingApplication {

    public static void main(String[] args) throws InterruptedException {

        SpringApplication.run(
                MultiThreadingApplication.class, args
        );

//        OddEvenPrint oddEvenPrint = new OddEvenPrint();
//
//        Thread oddThread = new Thread(oddEvenPrint::printOdd, "Odd-Thread");
//        Thread evenThread = new Thread(oddEvenPrint::printEven, "Even-Thread");
//
//        oddThread.start();
//        System.out.println(oddThread.getName());
//        evenThread.start();

        ParallelExecution parallelExecution = new ParallelExecution();
        parallelExecution.executorService();

        Thread t1 = new Thread(() ->
                System.out.println("Task 1 executing")
        );

        Thread t2 = new Thread(() ->
                System.out.println("Task 2 executing")
        );

        Thread t3 = new Thread(() ->
                System.out.println("Task 3 executing")
        );

        t1.start();
        t2.start();
        t3.start();
    }

}
