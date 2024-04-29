package com.luke.fcmanagement.module.logging;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "logging")
@Data
public class LoggingProperties {
    private Set<String> ignoresPath;
}
