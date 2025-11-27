package com.smart.shop.dto;

import com.smart.shop.enums.OrderStatus;

import java.time.LocalDateTime;

public interface CommandeSummaryProjection {
    Long getId();
    LocalDateTime getDate();
    Double getTotal();
    OrderStatus getStatus();
}
