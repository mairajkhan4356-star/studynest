package com.studynest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StudyNestApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyNestApplication.class, args);
        System.out.println("\n==========================================================");
        System.out.println("  StudyNest - Study Center Seat Booking Portal");
        System.out.println("  Portal URL: http://localhost:8080");
        System.out.println("  Database Console: http://localhost:8080/h2-console");
        System.out.println("==========================================================\n");
    }
}
