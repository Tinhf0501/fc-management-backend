package com.luke.fcmanagement;

import com.luke.fcmanagement.module.common.service.impl.DatabaseDumpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@RequiredArgsConstructor
@EnableScheduling
public class FcManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(FcManagementApplication.class, args);
    }
}
