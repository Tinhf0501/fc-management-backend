package com.luke.fcmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class AuditConfig {

    @Bean("auditAwareImpl")
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("FC_MANAGEMENT");
    }

}
