package com.testeAmbev.testeAmbev.service;

import com.testeAmbev.testeAmbev.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class InternalProductService implements ProductService {

    private final Map<String, ProductDTO> products = new HashMap<>();

    public InternalProductService() {
        initializeProducts();
    }

    private void initializeProducts() {
        products.put("PROD-001", ProductDTO.builder()
                .id("PROD-001")
                .name("Cerveja Brahma 350ml")
                .price(new BigDecimal("3.50"))
                .available(true)
                .build());

        products.put("PROD-002", ProductDTO.builder()
                .id("PROD-002")
                .name("Cerveja Skol 350ml")
                .price(new BigDecimal("3.20"))
                .available(true)
                .build());

        products.put("PROD-003", ProductDTO.builder()
                .id("PROD-003")
                .name("Cerveja Antarctica 350ml")
                .price(new BigDecimal("3.30"))
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
        return productId != null && productId.startsWith("PROD-");
    }
}