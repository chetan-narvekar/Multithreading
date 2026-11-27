package com.java.multi_threading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Assignment1 {

    public void executeTask() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                5,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        for (int i = 1; i <= 20; i++) {
            int taskId = i;

            try {

                executor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " executing Task " + taskId);


                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println(Thread.currentThread().getName() + " completed Task " + taskId);
                });

                System.out.println(" Submitted Task " + taskId);
            } catch (Exception e) {
                System.out.println("Task " + taskId + " rejected!");
            }
        }
        executor.shutdown();

        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("All tasks processed.");
    }
}
