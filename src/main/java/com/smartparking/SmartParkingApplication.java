package com.smartparking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartParkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartParkingApplication.class, args);
        System.out.println("==================================================================");
        System.out.println("  SMART PARKING MANAGEMENT SYSTEM STARTED SUCCESSFULLY");
        System.out.println("  URL: http://localhost:8080");
        System.out.println("  H2 Console: http://localhost:8080/h2-console");
        System.out.println("==================================================================");
    }
}
