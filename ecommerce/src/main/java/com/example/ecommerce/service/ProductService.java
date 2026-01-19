package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(Product product) {
        product.setId(UUID.randomUUID().toString());
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(String id) {
        return productRepository.findById(id).orElseThrow();
    }

    public void reduceStock(String productId, int qty) {
        Product product = getById(productId);
        if (product.getStock() < qty) throw new RuntimeException();
        product.setStock(product.getStock() - qty);
        productRepository.save(product);
    }
}
