package com.dealersautocenter.inventory.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest> {

    @Override
    public String resolveTenantIdentifier(HttpServletRequest request) {
        return request.getHeader("X-Tenant-Id");
    }

    @Override
    public String resolveUserRole(HttpServletRequest request) {
        return request.getHeader("X-User-Role");
    }
}
