package com.luke.fcmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class FcManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(FcManagementApplication.class, args);
    }
}
