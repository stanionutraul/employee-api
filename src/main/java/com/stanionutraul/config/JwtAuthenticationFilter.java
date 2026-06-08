package com.stanionutraul.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();
        String authHeader = request.getHeader("Authorization");

        System.out.println("\n=== REQUEST ===");
        System.out.println("Path: " + path);
        System.out.println("Auth header: " + authHeader);

        if (
                path.equals("/api/v1/auth/login") ||
                        path.equals("/api/v1/auth/register")
        ) {
            System.out.println("SKIP JWT FILTER (public auth endpoint)");
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("NO TOKEN -> request goes as anonymous");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = authHeader.substring(7);
            System.out.println("JWT: " + jwt);

            String userEmail = jwtService.extractUsername(jwt);
            System.out.println("Extracted email: " + userEmail);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                System.out.println("Loaded user: " + userDetails.getUsername());

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    System.out.println("TOKEN VALID ✔");

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    System.out.println("TOKEN INVALID ❌");
                }
            }

        } catch (Exception e) {
            System.out.println("JWT FILTER ERROR: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}