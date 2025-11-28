package com.smart.shop.exeception;

public class PromoCodeAlreadyUsedException extends RuntimeException {
    public PromoCodeAlreadyUsedException(String message) {
        super(message);
    }
}
