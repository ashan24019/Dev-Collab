package com.devcollab.devcollab.config;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtils {

    public static String getCurrentUserId() {
        return (String) Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                        .getPrincipal();
    }

    public static String getCurrentUserRole() {
        return Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getAuthorities()
                .stream()
                .findFirst()
                .map(a-> Objects.requireNonNull(a.getAuthority()).replace("ROLE_", ""))
                .orElse("MEMBER");
    }
}
