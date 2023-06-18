package com.yarosh.checks.domain.exception;

import java.text.MessageFormat;

public class InvalidProductException extends RuntimeException {

    public InvalidProductException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }

    public static InvalidProductException quantityCase(long id, Integer quantity) {
        return new InvalidProductException("Quantity of product is less than 1 or null, value: {0}, quantity: {1}", id, quantity);
    }
}
