package com.phoebe.pbsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PB Client Reporting Subscription System Main Application
 * 
 * @author FX Prime Brokerage Team
 * @version 1.0.0
 */
@SpringBootApplication
public class PbSubApplication {

    public static void main(String[] args) {
        SpringApplication.run(PbSubApplication.class, args);
        
        System.out.println("\n" +
            "╔══════════════════════════════════════════════════════════════╗\n" +
            "║                                                              ║\n" +
            "║    🏦 PB Client Reporting Subscription System 🏦            ║\n" +
            "║                                                              ║\n" +
            "║    ✅ Application started successfully!                      ║\n" +
            "║                                                              ║\n" +
            "║    📱 Web Interface: http://localhost:8080/clients           ║\n" +
            "║                                                              ║\n" +
            "║    🎯 Features:                                              ║\n" +
            "║    • Client Management (CRUD)                                ║\n" +
            "║    • Report Subscription Management                          ║\n" +
            "║    • REST API Documentation                                  ║\n" +
            "║    • Data Validation & Exception Handling                    ║\n" +
            "║                                                              ║\n" +
            "║    💡 Tech Stack: Spring Boot 3.5.4 + JPA + Thymeleaf + H2   ║\n" +
            "║                                                              ║\n" +
            "╚══════════════════════════════════════════════════════════════╝\n");
    }
}
