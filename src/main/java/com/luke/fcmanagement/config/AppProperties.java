package com.luke.fcmanagement.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "media")
@Getter
@Setter
public class AppProperties {
    private String absolutePath;
    private String memberPath;
    private String fcImgPath;
    private String fcLogoPath;
    private String fcVideoPath;
}
