package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The DemoApplication class serves as the entry point for the Spring Boot application.
 * It contains the main method to start the application using SpringApplication.
 */
@SpringBootApplication
public class DemoApplication {

    /**
     * The main method is the entry point for the Spring Boot application.
     * It calls SpringApplication.run() to start the application.
     *
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
