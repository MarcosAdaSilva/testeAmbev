package com.testeAmbev.testeAmbev.infrastructure.exception;

public class DuplicateOrderException extends RuntimeException {

    public DuplicateOrderException(String orderNumber) {
        super(String.format("Order already exists with number: %s", orderNumber));
    }
}