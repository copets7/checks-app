package com.yarosh.checks.domain.exception;

import java.text.MessageFormat;

public class InvalidQuantityException extends RuntimeException {

    public InvalidQuantityException(long id, Integer quantity) {
        this("Quantity of product is less than 1 or null, id: {0}, quantity: {1}", id, quantity);
    }

    public InvalidQuantityException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
