package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartRequest {
    @NotNull
    private String userId;
    @NotNull
    private String productId;
    @NotNull
    private Integer quantity;
}
