package com.dealersautocenter.inventory.domain.exception;

public class CrossTenantAccessException extends RuntimeException {
    public CrossTenantAccessException(String msg) { super(msg); }
}
