package com.luke.fcmanagement.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "media.saver.local")
@ConditionalOnProperty(prefix = "media.saver", value = "active", havingValue = "local")
@Setter
@Getter
public class LocalSaverFileConfig {
    private String absolutePath;
}
