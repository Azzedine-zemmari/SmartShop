package com.smart.shop.exeception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPromoCodeException extends RuntimeException {
    public InvalidPromoCodeException(String message) {
        super(message);
    }
}
