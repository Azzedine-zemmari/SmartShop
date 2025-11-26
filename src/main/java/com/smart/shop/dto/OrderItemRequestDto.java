package com.smart.shop.dto;

import lombok.Data;

@Data
public class OrderItemRequestDto {
    private Integer productId;
    private Integer quantity;
}
