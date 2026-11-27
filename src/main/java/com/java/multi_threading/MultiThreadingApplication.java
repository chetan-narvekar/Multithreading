
package com.java.multi_threading;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.*;

@SpringBootApplication
public class MultiThreadingApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MultiThreadingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Assignment1 assignment1 = new Assignment1();
        assignment1.executeTask();
    }
}
