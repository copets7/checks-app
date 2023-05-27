package com.yarosh.checks.domain.exception;

import java.text.MessageFormat;

public class InvalidCheckException extends RuntimeException {

    public InvalidCheckException(Double totalPrice) {
        this("Total price in check is less than 0.1 or null, price: {0}", totalPrice);
    }

    public InvalidCheckException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
