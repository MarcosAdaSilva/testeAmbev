package com.testeAmbev.testeAmbev.infrastructure.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String productId) {
        super(String.format("Product not found: %s", productId));
    }
}