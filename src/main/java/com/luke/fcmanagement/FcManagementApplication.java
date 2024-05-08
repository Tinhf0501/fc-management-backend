package com.luke.fcmanagement;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class FcManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(FcManagementApplication.class, args);
    }
}
