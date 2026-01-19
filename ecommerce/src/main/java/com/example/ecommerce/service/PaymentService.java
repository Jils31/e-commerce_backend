package com.example.ecommerce.service;

import com.example.ecommerce.dto.PaymentRequest;
import com.example.ecommerce.model.Payment;
import com.example.ecommerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MockPaymentClient mockPaymentClient;

    public Payment create(PaymentRequest request) {
        Payment payment = paymentRepository.save(
                Payment.builder()
                        .id(UUID.randomUUID().toString())
                        .orderId(request.getOrderId())
                        .amount(request.getAmount())
                        .status("PENDING")
                        .paymentId("pay_mock_" + UUID.randomUUID())
                        .createdAt(Instant.now())
                        .build()
        );

        mockPaymentClient.process(payment);
        return payment;
    }

    public Payment getByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId).orElseThrow();
    }

    public void update(String orderId, String status) {
        Payment payment = getByOrderId(orderId);
        payment.setStatus(status);
        paymentRepository.save(payment);
    }
}
