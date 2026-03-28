package com.dealersautocenter.inventory.security;

import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String tenantId = request.getHeader("X-Tenant-Id");
        String role = request.getHeader("X-User-Role");

        try {
            if (tenantId != null) {
                TenantContext.setTenantId(tenantId);
                MDC.put("tenantId", tenantId);
            }
            if (role != null) {
                TenantContext.setUserRole(role);
                MDC.put("userRole", role);
            }

            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
            // Clear only the keys we added, or all of MDC. clearing all is safer standard if we don't carry other MDC keys between requests
            MDC.remove("tenantId");
            MDC.remove("userRole");
        }
    }
}
