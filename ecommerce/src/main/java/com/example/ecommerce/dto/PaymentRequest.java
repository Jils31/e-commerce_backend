package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {
    @NotNull
    private String orderId;
    @NotNull
    private Double amount;
}
