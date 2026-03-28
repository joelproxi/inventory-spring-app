package com.dealersautocenter.inventory.infrastructure.security;

/**
 * Stores the tenant and user role for the current request (ThreadLocal).
 */
public final class TenantContext {

    private static final ThreadLocal<String> TENANT = new ThreadLocal<>();
    private static final ThreadLocal<String> ROLE   = new ThreadLocal<>();

    private TenantContext() {}

    public static String getTenantId() { return TENANT.get(); }
    public static void setTenantId(String id) { TENANT.set(id); }

    public static String getUserRole() { return ROLE.get(); }
    public static void setUserRole(String role) { ROLE.set(role); }

    public static void clear() {
        TENANT.remove();
        ROLE.remove();
    }
}