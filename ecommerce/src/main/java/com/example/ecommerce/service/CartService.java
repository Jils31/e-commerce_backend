package com.example.ecommerce.service;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartItem add(AddToCartRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow();
        if (product.getStock() < request.getQuantity()) throw new RuntimeException();

        return cartRepository.findByUserIdAndProductId(request.getUserId(), request.getProductId())
                .map(item -> {
                    item.setQuantity(item.getQuantity() + request.getQuantity());
                    return cartRepository.save(item);
                })
                .orElseGet(() -> cartRepository.save(
                        CartItem.builder()
                                .id(UUID.randomUUID().toString())
                                .userId(request.getUserId())
                                .productId(request.getProductId())
                                .quantity(request.getQuantity())
                                .build()
                ));
    }

    public List<CartItem> getUserCart(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public void clear(String userId) {
        cartRepository.deleteByUserId(userId);
    }
}
