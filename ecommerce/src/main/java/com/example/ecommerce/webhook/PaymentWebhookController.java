package com.example.ecommerce.webhook;

import com.example.ecommerce.dto.PaymentWebhookRequest;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhooks")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @PostMapping("/payment")
    public void handle(@RequestBody PaymentWebhookRequest request) {
        paymentService.update(request.getOrderId(), request.getStatus());
        orderService.updateStatus(
                request.getOrderId(),
                request.getStatus().equals("SUCCESS") ? "PAID" : "FAILED"
        );
    }
}
