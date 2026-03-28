package com.dealersautocenter.inventory.exception;

public class ForbiddenAccessException extends RuntimeException {
    public ForbiddenAccessException(String msg) { super(msg); }
}
