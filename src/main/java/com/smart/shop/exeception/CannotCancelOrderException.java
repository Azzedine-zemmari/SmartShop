package com.smart.shop.exeception;

public class CannotCancelOrderException extends RuntimeException {
    public CannotCancelOrderException(String message) {
        super(message);
    }
}
