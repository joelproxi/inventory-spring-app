package com.dealersautocenter.inventory.security;

public interface TenantResolver<T> {
    String resolveTenantIdentifier(T request);
    String resolveUserRole(T request);
}
