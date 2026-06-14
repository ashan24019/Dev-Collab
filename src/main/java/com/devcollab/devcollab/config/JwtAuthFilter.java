package com.devcollab.devcollab.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Step 1 - Get Authorization header
        String authHeader = request.getHeader("Authorization");

        // Step 2 - Check if it starts with "Bearer"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // no token, move on
            return;
        }

        // Step 3 - Extract token (remove "Bearer " prefix)
        String token = authHeader.substring(7);

        // Step 4 - Validation token
        if (!jwtService.isTokenValid(token)) {
            filterChain.doFilter(request, response); // invalid token, move on
            return;
        }

        // Step 5 - Extract user info from token
        String UserId = jwtService.extractUserId(token);
        String role = jwtService.extractRole(token);

        // Step 6 - Set authentication in SecurityContext
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        UserId,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Step 7 - Continue to next filter
        filterChain.doFilter(request, response);
    }
}
