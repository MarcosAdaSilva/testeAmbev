package com.testeAmbev.testeAmbev.service;

import com.testeAmbev.testeAmbev.dto.ProductDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ExternalProductServiceB implements ProductService {

    private final Map<String, ProductDTO> products = new HashMap<>();

    public ExternalProductServiceB() {
        initializeProducts();
    }

    private void initializeProducts() {
        products.put("EXT-B-001", ProductDTO.builder()
                .id("EXT-B-001")
                .name("Água Mineral 500ml")
                .price(new BigDecimal("2.50"))
                .available(true)
                .build());

        products.put("EXT-B-002", ProductDTO.builder()
                .id("EXT-B-002")
                .name("Suco Natural 1L")
                .price(new BigDecimal("6.90"))
                .available(true)
                .build());
    }

    @Override
    @Cacheable(value = "products", key = "#productId")
    public Optional<ProductDTO> findProduct(String productId) {
        return Optional.ofNullable(products.get(productId));
    }

    @Override
    public boolean supports(String productId) {
        return productId != null && productId.startsWith("EXT-B-");
    }
}