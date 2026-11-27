package com.java.multi_threading;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncOperations {

// Async Operation

    @Async
    public CompletableFuture<String> fetchUserDataAsync() throws InterruptedException {
        System.out.println(
                Thread.currentThread().getName()
                        + " - Fetching user data"
        );

        sleep(2000);

        return CompletableFuture.completedFuture(
                "User Details: Chetan, Age: 25"
        );
    }

    @Async
    public CompletableFuture<String> fetchOrderDataAsync() throws InterruptedException {
        System.out.println(
                Thread.currentThread().getName()
                        + " - Fetching order data"
        );

        sleep(3000);

        return CompletableFuture.completedFuture(
                "Order Details: 5 orders found"
        );
    }

    @Async
    public CompletableFuture<String> fetchPaymentDataAsync() throws InterruptedException {
        System.out.println(
                Thread.currentThread().getName()
                        + " - Fetching payment history"
        );

        sleep(4000);

        return CompletableFuture.completedFuture(
                "Payment History: 10 transactions found"
        );
    }

    private void sleep(long milliseconds) {

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

}
