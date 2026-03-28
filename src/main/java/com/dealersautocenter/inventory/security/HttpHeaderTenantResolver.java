package com.dealersautocenter.inventory.security;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest> {

    private static final String TENANT_HEADER = "X-Tenant-Id";
    private static final String ROLE_HEADER = "X-User-Role";

    @Override
    public String resolveTenantIdentifier(HttpServletRequest request) {
        return request.getHeader(TENANT_HEADER);
    }

    @Override
    public String resolveUserRole(HttpServletRequest request) {
        return request.getHeader(ROLE_HEADER);
    }
}
