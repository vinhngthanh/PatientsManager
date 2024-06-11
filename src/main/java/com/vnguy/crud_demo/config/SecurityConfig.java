package com.vnguy.crud_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers(HttpMethod.POST, "/auth/*").permitAll();
                    registry.requestMatchers(HttpMethod.GET, "/patients").hasAnyRole("USER", "ADMIN");
                    registry.requestMatchers(HttpMethod.POST, "/patients").hasRole("ADMIN");
                    registry.requestMatchers(HttpMethod.PUT, "/patients/{id}").hasRole("ADMIN");
                    registry.requestMatchers(HttpMethod.DELETE, "/patients/{id}").hasRole("ADMIN");
                    registry.anyRequest().authenticated();
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}