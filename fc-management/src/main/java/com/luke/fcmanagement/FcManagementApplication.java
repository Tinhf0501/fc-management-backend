package com.luke.fcmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class FcManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(FcManagementApplication.class, args);
    }

}
