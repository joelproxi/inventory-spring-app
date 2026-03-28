package com.dealersautocenter.inventory.security;



/**
 * Strategy used to resolve the current tenant (and user role) from a given request.
 */
public interface TenantResolver<T> {

    String resolveTenantIdentifier(T request);

    String resolveUserRole(T request);
}
