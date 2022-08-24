package com.example.productsapi.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
    }
    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
