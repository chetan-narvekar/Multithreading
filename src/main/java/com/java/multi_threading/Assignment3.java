package com.java.multi_threading;

import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class Assignment3 {


    public void retryRejectExecutionHandler(){

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                5,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new RetryRejectedExecutionHandler()
        );

        for ( int i = 0 ; i < 20 ; i++){
            int taskId = i;

            executor.execute(()->{
                System.out.println(
                        Thread.currentThread().getName()
                                + " executing Task " + taskId
                );

                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Task interrupted: " + taskId);
                }

                System.out.println("Task " + taskId + " completed.");
            });
        }

        executor.shutdown();

        try {
            if (!executor.awaitTermination(2, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("All tasks processed.");
    }
}
