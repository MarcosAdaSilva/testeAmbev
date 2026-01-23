package com.testeAmbev.testeAmbev.infrastructure.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long orderId) {
        super(String.format("Order not found with id: %d", orderId));
    }

    public OrderNotFoundException(String orderNumber) {
        super(String.format("Order not found with number: %s", orderNumber));
    }
}