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

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        return http

                // =========================
                // CORS
                // =========================
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // =========================
                // CSRF
                // =========================
                .csrf(csrf -> csrf.disable())

                // =========================
                // AUTHORIZATION
                // =========================
                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // PUBLIC
                        // =========================
                        .requestMatchers(
                                "/api/v1/auth/register",
                                "/api/v1/auth/login",
                                "/api/v1/auth/verify",
                                "/api/v1/auth/resend-verification",
                                "/api/v1/auth/forgot-password",
                                "/api/v1/auth/reset-password"
                        ).permitAll()

                        // =========================
                        // CURRENT USER
                        // =========================
                        .requestMatchers("/api/v1/auth/me")
                        .authenticated()

                        // =========================
                        // TRAINERS (ADMIN ONLY)
                        // =========================
                        .requestMatchers("/api/v1/trainers/**")
                        .hasRole("ADMIN")

                        // =========================
                        // WORKOUTS
                        // =========================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/workouts/**"
                        )
                        .hasAnyRole("USER", "TRAINER", "ADMIN")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/workouts/**"
                        )
                        .hasAnyRole("TRAINER", "ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/workouts/**"
                        )
                        .hasAnyRole("TRAINER", "ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/workouts/**"
                        )
                        .hasAnyRole("TRAINER", "ADMIN")

                        // =========================
                        // USER WORKOUTS
                        // =========================
                        .requestMatchers("/api/v1/user-workouts/**")
                        .hasAnyRole("USER", "TRAINER", "ADMIN")

                        // =========================
                        // PROFILE
                        // =========================
                        .requestMatchers("/api/v1/profile/**")
                        .authenticated()

                        // =========================
                        // WORKOUT EXERCISES
                        // =========================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/workouts/*/exercises"
                        )
                        .hasAnyRole("USER", "TRAINER", "ADMIN")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/workouts/*/exercises"
                        )
                        .hasAnyRole("TRAINER", "ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/v1/workout-exercises/**"
                        )
                        .hasAnyRole("TRAINER", "ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/workout-exercises/**"
                        )
                        .hasAnyRole("TRAINER", "ADMIN")

                        // =========================
                        // TRAINER AREA
                        // =========================
                        .requestMatchers("/api/v1/trainer/**")
                        .hasAnyRole("TRAINER", "ADMIN")

                        .anyRequest()
                        .authenticated()
                )

                // =========================
                // STATELESS SESSION
                // =========================
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // =========================
                // AUTH PROVIDER
                // =========================
                .authenticationProvider(authenticationProvider)

                // =========================
                // JWT FILTER
                // =========================
                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:5173",
                "https://*.vercel.app"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}