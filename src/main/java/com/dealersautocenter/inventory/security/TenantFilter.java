package com.dealersautocenter.inventory.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Filtre HTTP :
 * - Extrait X-Tenant-Id (obligatoire) et X-User-Role (optionnel)
 * - Renvoie 400 si X-Tenant-Id absent
 */
@Component
@Order(1)
public class TenantFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpReq  = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;
        String path = httpReq.getRequestURI();

        // Exclure les chemins utilitaires
        if (path.startsWith("/h2-console") || path.startsWith("/swagger")
                || path.startsWith("/v3/api-docs")) {
            chain.doFilter(req, res);
            return;
        }

        String tenantId = httpReq.getHeader("X-Tenant-Id");
        if (tenantId == null || tenantId.isBlank()) {
            httpRes.setStatus(400);
            httpRes.setContentType("application/json");
            httpRes.getWriter().write(
                    "{\"error\": \"Missing required header: X-Tenant-Id\"}");
            return;
        }

        String role = httpReq.getHeader("X-User-Role");

        try {
            TenantContext.setTenantId(tenantId.trim());
            TenantContext.setUserRole(role != null ? role.trim() : "TENANT_USER");
            chain.doFilter(req, res);
        } finally {
            TenantContext.clear();
        }
    }
}
