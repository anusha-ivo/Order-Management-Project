package com.ordermanagement.payment.service.service;

import com.ordermanagement.payment.service.entity.Payment;
import com.ordermanagement.payment.service.entity.PaymentRequest;
import com.ordermanagement.payment.service.entity.PaymentResponse;
import com.ordermanagement.payment.service.entity.PaymentStatus;
import com.ordermanagement.payment.service.exceptions.InvalidPaymentStateException;
import com.ordermanagement.payment.service.exceptions.PaymentAlreadyExistsException;
import com.ordermanagement.payment.service.exceptions.PaymentNotFoundException;
import com.ordermanagement.payment.service.repository.PaymentRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;
    public PaymentResponse capturePayment(PaymentRequest request) {
        Payment exist = repository.findByIdempotencyKey(request.getIdempotencyKey());
        if (exist != null) {
            return mapToResponse(exist);
        }   Payment orderPayment =
                repository.findByOrderId(request.getOrderId());

        if (orderPayment != null) {
            throw new PaymentAlreadyExistsException("Order already has payment");
        }
        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setMethod(request.getMethod());
        payment.setStatus(PaymentStatus.CAPTURED);
        payment.setIdempotencyKey(request.getIdempotencyKey());
        payment.setCreatedAt(Instant.now());
        payment.setUpdatedAt(Instant.now());
        Long generatedId = repository.save(payment);
        payment.setPaymentId(generatedId);

        return mapToResponse(payment);
    }  public PaymentResponse refund(Long id) {

        Payment payment = repository.findById(id);

        if (payment == null) {
            throw new PaymentNotFoundException("Payment not found");
        }

        if (payment.getStatus() != PaymentStatus.CAPTURED) {
            throw new InvalidPaymentStateException("Only CAPTURED payment can be refunded");
        }

        repository.updateStatus(id, PaymentStatus.REFUNDED);

        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setUpdatedAt(Instant.now());

        return mapToResponse(payment);
    }
    public PaymentResponse getPayment(Long id) {

        Payment payment = repository.findById(id);

        if (payment == null) {
            throw new PaymentNotFoundException("Payment not found");
        }

        return mapToResponse(payment);
    }

    private PaymentResponse mapToResponse(Payment payment) {

        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .method(payment.getMethod())
                .status(String.valueOf(payment.getStatus()))
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
