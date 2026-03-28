package com.dealersautocenter.inventory.infrastructure.security;

public interface TenantResolver<T> {
    String resolveTenantIdentifier(T context);
    String resolveUserRole(T context);
}
