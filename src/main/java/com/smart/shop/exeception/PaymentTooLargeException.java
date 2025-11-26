package com.smart.shop.exeception;

public class PaymentTooLargeException extends RuntimeException {
    public PaymentTooLargeException(String message) {
        super(message);
    }
}
