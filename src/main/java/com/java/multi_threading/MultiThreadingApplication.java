
package com.java.multi_threading;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MultiThreadingApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                MultiThreadingApplication.class, args
        );
    }

    @Bean
    public CommandLineRunner run(Assignment2 assignment2) {

        return args -> {
            assignment2.getUserReportAsync();
        };
    }
}
