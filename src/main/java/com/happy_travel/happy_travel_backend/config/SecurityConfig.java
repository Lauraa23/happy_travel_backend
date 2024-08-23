package com.happy_travel.happy_travel_backend.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Desactiva CSRF para permitir llamadas desde el frontend
            .cors(withDefaults())           // Habilita CORS con la configuración que se define más adelante
            .authorizeHttpRequests(authRequest -> authRequest
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/destinations/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/destinations/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(withDefaults());

        return http.build();
    }

    
    
}
