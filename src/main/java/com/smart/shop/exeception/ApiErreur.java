package com.smart.shop.exeception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiErreur {
    private String message;
    private LocalDateTime localDateTime;
    private Integer Status;

}
