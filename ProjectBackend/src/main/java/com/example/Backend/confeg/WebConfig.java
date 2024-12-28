package com.example.Backend.confeg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all paths
                .allowedOrigins("http://localhost:5173") // Replace with your frontend origin
                .allowedMethods("*") // Allow all methods (GET, POST, etc.)
                .allowedHeaders("*") // Allow all headers
                .allowCredentials(true); // Allow credentials if necessary (e.g., cookies or authorization headers)
    }

    
}