package com.szabolcs.SpringbootWebshop.ExceptionHandler.Exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
