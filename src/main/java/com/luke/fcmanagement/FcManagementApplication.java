package com.luke.fcmanagement;

//import com.luke.fcmanagement.module.common.service.impl.DatabaseDumpService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableScheduling
public class FcManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(FcManagementApplication.class, args);
    }
}
