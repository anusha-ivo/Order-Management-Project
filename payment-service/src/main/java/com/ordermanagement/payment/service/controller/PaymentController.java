package com.ordermanagement.payment.service.controller;

import com.ordermanagement.payment.service.entity.PaymentRequest;
import com.ordermanagement.payment.service.entity.PaymentResponse;
import com.ordermanagement.payment.service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;
    @PostMapping
    public ResponseEntity<PaymentResponse> capturePayment(
            @RequestBody PaymentRequest request) {

        PaymentResponse response = service.capturePayment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id){
      PaymentResponse response =service.getPayment(id);
      return ResponseEntity.ok(response);
    }
    @PostMapping("/{id}/refund")
    public ResponseEntity<PaymentResponse> refundPayment(
            @PathVariable Long id) {

        PaymentResponse response = service.refund(id);

        return ResponseEntity.ok(response);
    }
}
