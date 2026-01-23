package com.testeAmbev.testeAmbev.service;

import com.testeAmbev.testeAmbev.dto.ProductDTO;
import com.testeAmbev.testeAmbev.infrastructure.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductServiceResolver {

    private final List<ProductService> productServices;

    public ProductDTO resolveProduct(String productId) {
        return productServices.stream()
                .filter(service -> service.supports(productId))
                .findFirst()
                .flatMap(service -> service.findProduct(productId))
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
}