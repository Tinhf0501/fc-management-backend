package com.luke.fcmanagement.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Configuration
public class SecurityConfig {
    @Value("${frontend.url}")
    public String feUrl;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        List<String> urls;
        if (Objects.nonNull(feUrl))
            urls = List.of(feUrl);
        else
            urls = Collections.emptyList();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(urls);
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(
                Arrays.asList(
                        "Access-Control-Allow-Headers",
                        "Access-Control-Allow-Origin",
                        "Access-Control-Request-Method",
                        "Access-Control-Request-Headers",
                        "Origin", "Cache-Control",
                        "Content-Type", "Authorization")
        );
        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PUT", "PATCH"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
