package com.java.multi_threading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class RetryRejectedExecutionHandler implements RejectedExecutionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(RetryRejectedExecutionHandler.class);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        log.warn("Task rejected , Retrying after 1 seconds ");

        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if( !executor.isShutdown()){
            executor.execute(r);
            log.info("Rejected task submitted successfully");
        }else{
            log.warn("Executor is shut down. Task cannot be retried.");
        }
    }
}
