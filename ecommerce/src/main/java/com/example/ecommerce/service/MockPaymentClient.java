package com.example.ecommerce.service;

import com.example.ecommerce.dto.PaymentWebhookRequest;
import com.example.ecommerce.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MockPaymentClient {

    private final RestTemplate restTemplate;

    public void process(Payment payment) {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                PaymentWebhookRequest request = new PaymentWebhookRequest();
                request.setOrderId(payment.getOrderId());
                request.setPaymentId(payment.getPaymentId());
                request.setStatus("SUCCESS");
                restTemplate.postForObject(
                        "http://localhost:8080/api/webhooks/payment",
                        request,
                        Void.class
                );
            } catch (Exception ignored) {
            }
        }).start();
    }
}
