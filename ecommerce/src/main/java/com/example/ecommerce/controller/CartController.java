package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public CartItem add(@RequestBody AddToCartRequest request) {
        return cartService.add(request);
    }

    @GetMapping("/{userId}")
    public List<CartItem> get(@PathVariable String userId) {
        return cartService.getUserCart(userId);
    }

    @DeleteMapping("/{userId}/clear")
    public void clear(@PathVariable String userId) {
        cartService.clear(userId);
    }
}
