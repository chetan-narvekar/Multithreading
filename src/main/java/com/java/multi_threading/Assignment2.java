package com.java.multi_threading;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

@Service
public class Assignment2 {

    private final AsyncOperations asyncOperations;

    public Assignment2(AsyncOperations asyncDataService) {
        this.asyncOperations = asyncDataService;
    }

// Sync operation
    public String fetchUserDate() throws InterruptedException {
        sleep(2000);
        return "OK-Data";
    }

    public String fetchOrderData() throws InterruptedException {
        sleep(3000);
        return "OK-Order";
    }

    public String fetchPaymentData() throws InterruptedException {
        sleep(4000);
        return "OK-Pay";
    }

    public void getUserReport() throws InterruptedException {

        long start = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        fetchOrderData();
        fetchUserDate();
        fetchPaymentData();

        long end = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        System.out.println("TIME required :" + (end - start));

    }

    public void getUserReportAsync() throws InterruptedException {

        long startTime = System.currentTimeMillis();

        CompletableFuture<String> userFuture = asyncOperations.fetchUserDataAsync();
        CompletableFuture<String> orderFuture = asyncOperations.fetchOrderDataAsync();
        CompletableFuture<String> paymentFuture = asyncOperations.fetchPaymentDataAsync();


        CompletableFuture.allOf(userFuture,
                orderFuture, paymentFuture).join();

        String user = userFuture.join();
        String order = orderFuture.join();
        String payment = paymentFuture.join();

        // Combine results

        System.out.println("\n========== USER REPORT ==========");

        System.out.println(user);
        System.out.println(order);
        System.out.println(payment);

        long endTime = System.currentTimeMillis();

        System.out.println(
                "Total execution time: "
                        + (endTime - startTime) + " ms"
        );
    }
}
