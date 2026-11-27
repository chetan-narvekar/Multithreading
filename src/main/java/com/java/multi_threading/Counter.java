package com.java.multi_threading;

public class Counter {

    int counter = 0;

    public synchronized void increment() throws InterruptedException {
        counter++;
        Thread.sleep(5000);
    }
}
