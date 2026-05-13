package com.stanionutraul.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // PUBLIC
                        // =========================
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()

                        // =========================
                        // TRAINERS (ADMIN ONLY)
                        // =========================
                        .requestMatchers("/api/v1/trainers/**")
                        .hasRole("ADMIN")

                        // =========================
                        // WORKOUTS
                        // =========================

                        // VIEW workouts
                        .requestMatchers(HttpMethod.GET, "/api/v1/workouts/**")
                        .hasAnyRole("USER", "TRAINER", "ADMIN")

                        // CREATE workout
                        .requestMatchers(HttpMethod.POST, "/api/v1/workouts/**")
                        .hasAnyRole("TRAINER", "ADMIN")

                        // UPDATE workout
                        .requestMatchers(HttpMethod.PUT, "/api/v1/workouts/**")
                        .hasAnyRole("TRAINER", "ADMIN")

                        // DELETE workout
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/workouts/**")
                        .hasAnyRole("TRAINER", "ADMIN")

                        // =========================
                        // USER WORKOUTS
                        // =========================
                        .requestMatchers("/api/v1/user-workouts/**")
                        .hasRole("USER")

                        // =========================
                        // EVERYTHING ELSE
                        // =========================
                        .anyRequest()
                        .authenticated()
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authenticationProvider(authenticationProvider)

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }
}