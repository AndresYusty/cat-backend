package com.cat_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())            // Desactiva CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()               // Permite todas las rutas
                )
                .httpBasic(basic -> basic.disable());     // Desactiva Basic Auth

        return http.build();
    }
}
