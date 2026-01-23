package com.testeAmbev.testeAmbev.service;

import com.testeAmbev.testeAmbev.dto.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ExternalProductServiceA implements ProductService {

    private final Map<String, ProductDTO> products = new HashMap<>();

    public ExternalProductServiceA() {
        initializeProducts();
    }

    private void initializeProducts() {
        products.put("EXT-A-001", ProductDTO.builder()
                .id("EXT-A-001")
                .name("Refrigerante Coca-Cola 2L")
                .price(new BigDecimal("8.50"))
                .available(true)
                .build());

        products.put("EXT-A-002", ProductDTO.builder()
                .id("EXT-A-002")
                .name("Refrigerante Guaraná 2L")
                .price(new BigDecimal("7.80"))
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
        return productId != null && productId.startsWith("EXT-A-");
    }
}
