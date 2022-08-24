package com.example.productsapi.exception;

public class ProductIdMismatchException extends RuntimeException {

    public ProductIdMismatchException() {

    }
    public ProductIdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
