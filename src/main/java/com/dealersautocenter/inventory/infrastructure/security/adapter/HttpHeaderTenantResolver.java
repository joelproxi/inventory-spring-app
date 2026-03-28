package com.dealersautocenter.inventory.infrastructure.security.adapter;

import com.dealersautocenter.inventory.infrastructure.security.TenantResolver;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest> {

    @Value("${tenant.header-name:X-Tenant-Id}")
    private String tenantHeaderName;

    @Value("${tenant.role-header-name:X-User-Role}")
    private String roleHeaderName;

    @Override
    public String resolveTenantIdentifier(HttpServletRequest request) {
        return request.getHeader(tenantHeaderName);
    }

    @Override
    public String resolveUserRole(HttpServletRequest request) {
        return request.getHeader(roleHeaderName);
    }
}
