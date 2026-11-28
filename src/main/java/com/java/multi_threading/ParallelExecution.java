package com.java.multi_threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ParallelExecution {

    public void executorService() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for ( int i = 0; i < 10; i++){
            int taskId = i;
            executor.submit(()-> {
                System.out.println("Task"+ taskId+ "Executed by "+ Thread.currentThread().getName());
            });
            Thread.sleep(1000);
        }

    }
}
