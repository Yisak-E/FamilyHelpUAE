package com.example.familyhelpuae.configuration; // Adjust package name if needed

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Allow your Next.js frontend
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));

        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        
        // Allow all headers (Authorization, Content-Type, etc.)
        config.setAllowedHeaders(Arrays.asList("*"));
        
        // Allow all methods (GET, POST, PUT, PATCH, DELETE, OPTIONS)
        config.setAllowedMethods(Arrays.asList("*"));
        
        // Allow credentials (important if you eventually use cookies/sessions)
        config.setAllowCredentials(true);
        
        // Apply this to all API endpoints
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}