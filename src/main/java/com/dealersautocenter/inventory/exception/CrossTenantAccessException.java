package com.dealersautocenter.inventory.exception;

public class CrossTenantAccessException extends RuntimeException {
    public CrossTenantAccessException(String msg) { super(msg); }
}
