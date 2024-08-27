package com.happy_travel.happy_travel_backend.config;

import com.happy_travel.happy_travel_backend.jwt.JwtAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private final AuthenticationProvider authProvider;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
            .csrf(csrf -> csrf.disable())  // Desactiva CSRF para permitir llamadas desde el frontend
            .cors(withDefaults())           // Habilita CORS con la configuración que se define más adelante
            .authorizeHttpRequests(authRequest -> authRequest
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/destinations/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/destinations/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/destinations/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/destinations/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(sessionManager->
                sessionManager 
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore((Filter) jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

    
    
}
