package com.ParkingSystem.Parking.System.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF and allow all requests
        http.csrf(csrf -> csrf.disable())  // Disable CSRF protection
                .authorizeRequests(authz -> authz
                        .anyRequest().permitAll()  // Allow all requests without authentication
                );
        return http.build();
    }
}
