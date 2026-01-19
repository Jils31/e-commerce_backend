package com.example.ecommerce.service;

import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;

    public Order createOrder(String userId) {
        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) throw new RuntimeException();

        double total = 0;
        for (CartItem item : cartItems) {
            Product product = productService.getById(item.getProductId());
            if (product.getStock() < item.getQuantity()) throw new RuntimeException();
            total += product.getPrice() * item.getQuantity();
        }

        Order order = orderRepository.save(
                Order.builder()
                        .id(UUID.randomUUID().toString())
                        .userId(userId)
                        .totalAmount(total)
                        .status("CREATED")
                        .createdAt(Instant.now())
                        .build()
        );

        for (CartItem item : cartItems) {
            Product product = productService.getById(item.getProductId());
            orderItemRepository.save(
                    OrderItem.builder()
                            .id(UUID.randomUUID().toString())
                            .orderId(order.getId())
                            .productId(product.getId())
                            .quantity(item.getQuantity())
                            .price(product.getPrice())
                            .build()
            );
            productService.reduceStock(product.getId(), item.getQuantity());
        }

        cartRepository.deleteByUserId(userId);
        return order;
    }

    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }

    public void updateStatus(String orderId, String status) {
        Order order = getOrder(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }
}
