package com.dealersautocenter.inventory.domain.exception;

public class ForbiddenAccessException extends RuntimeException {
    public ForbiddenAccessException(String msg) { super(msg); }
}
