package com.smart.shop.exeception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErreur {
    private String message;
    private LocalDateTime localDateTime;
    private Integer Status;
}
