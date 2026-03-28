package com.dealersautocenter.inventory.infrastructure.security.adapter;

import com.dealersautocenter.inventory.infrastructure.security.TenantResolver;
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
