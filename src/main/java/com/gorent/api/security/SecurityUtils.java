package com.gorent.api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static Long getCurrentAgencyId() {
        return ((DomainUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAgencyId();
    }

    public static Long getCurrentTenantId() {
        return ((DomainUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getTenantId();
    }

    public static Long getCurrentCountryId() {
        return ((DomainUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCountryId();
    }

    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
