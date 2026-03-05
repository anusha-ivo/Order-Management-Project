package com.ordermanagement.payment.service.entity;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Long paymentId;

    @NotNull
    private Long orderId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    @Size(min = 3, max = 3)
    private String currency;

    @NotBlank
    private String method;

    @NotNull
    private PaymentStatus status;

    @NotBlank
    private String idempotencyKey;


    private Instant createdAt;
    private Instant updatedAt;
}
