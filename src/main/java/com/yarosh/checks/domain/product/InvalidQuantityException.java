package com.yarosh.checks.domain.product;

import java.text.MessageFormat;

public class InvalidQuantityException extends RuntimeException {

    public InvalidQuantityException(long id, int quantity) {
        this("Quantity of product is less than 1, id: {0}, quantity: {1}", id, quantity);
    }

    public InvalidQuantityException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
