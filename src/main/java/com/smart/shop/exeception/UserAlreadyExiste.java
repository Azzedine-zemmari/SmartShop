package com.smart.shop.exeception;

public class UserAlreadyExiste extends RuntimeException {
    public UserAlreadyExiste(String message) {
        super(message);
    }
}
