
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

        Thread t2 = new Thread(()->{
            System.out.println("Thread t2 will be terminated");
        });

        t2.start();

        Thread t1 = new Thread(()->{
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(t1.getState());

        t1.start();

        Thread.sleep(2000);
        System.out.println(t2.getState());
        System.out.println(t1.getState());
        System.out.println("Main will wait: WAITING");
        t1.join();
        System.out.println("Main continues");
        System.out.println(Thread.currentThread().getState());

        Counter c = new Counter();

        Thread c1 = new Thread(()->{
            try {
                c.increment();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread c2 = new Thread(()->{
            try {
                c.increment();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        c1.start();
        Thread.sleep(1000);
        c2.start();
        Thread.sleep(1000);
        System.out.println(c2.getState());

    }

}
