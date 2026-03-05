package com.ordermanagement.payment.service.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long orderId;
    private BigDecimal amount;
    private String currency;
    private String method;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}
